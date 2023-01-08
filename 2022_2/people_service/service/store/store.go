package store

import (
	"context"
	"database/sql"
	"strconv"

	"errors"

	_ "github.com/golang-migrate/migrate/v4/source/file"
	"github.com/jackc/pgx/v4"
)

type Store struct {
	conn *pgx.Conn
}

type People struct {
	ID   int
	Name string
}

const (
	NotFound   = "not found"
	FailedScan = "scan failed"
)

func NewStore(connString string) (*Store, error) {
	conn, err := pgx.Connect(context.Background(), connString)
	if err != nil {
		return nil, err
	}

	db, err := sql.Open("postgres", connString)
	if err != nil {
		return nil, err
	}
	defer db.Close()

	err = migrations(db)
	if err != nil {
		return nil, err
	}

	return &Store{
		conn: conn,
	}, nil
}

func (s *Store) ListPeople() ([]People, error) {
	var people []People
	rows, err := s.conn.Query(context.Background(), "SELECT * FROM people")
	if err != nil {
		return nil, errors.New(NotFound + ": " + err.Error())
	}
	defer rows.Close()

	for rows.Next() {
		p := People{}
		if err := rows.Scan(&p.ID, &p.Name); err != nil {
			return nil, errors.New(FailedScan + ": " + err.Error())
		}
		people = append(people, p)
	}
	return people, nil
}

func (s *Store) GetPeopleByID(id string) (People, error) {
	p := People{}
	value, err := strconv.Atoi(id)
	if err != nil {
		return p, err
	}
	err = s.conn.QueryRow(context.Background(),
		"SELECT * FROM people WHERE id=$1", value).Scan(&p.ID, &p.Name)
	if err != nil {
		return p, errors.New(NotFound + ": " + err.Error())
	}
	return p, nil
}
