package store

import (
	"database/sql"
	"errors"
	"fmt"
	"os"

	"github.com/golang-migrate/migrate/v4"
	"github.com/golang-migrate/migrate/v4/database/postgres"
	"github.com/joho/godotenv"
)

func migrations(db *sql.DB) error {
	dbName, err := GetDbName()
	if err != nil {
		return err
	}

	d, err := postgres.WithInstance(db, &postgres.Config{
		DatabaseName: dbName,
		SchemaName:   "public",
	})
	if err != nil {
		return err
	}

	m, err := migrate.NewWithDatabaseInstance("file://../../migrations", dbName, d)
	if err != nil {
		return err
	}

	if err := m.Up(); err != nil && !errors.Is(err, migrate.ErrNoChange) {
		return err
	}
	return nil
}

func GetConnString() (string, error) {
	err := godotenv.Load("C:\\Users\\user\\people_service\\service\\configs\\config.env")
	if err != nil {
		return "", err
	}

	connectionString := fmt.Sprintf("postgres://%s:%s@%s/%s",
		os.Getenv("USER"),
		os.Getenv("PASS"),
		os.Getenv("HOST"),
		os.Getenv("NAME"),
	)

	return connectionString, err
}

func GetDbName() (string, error) {
	err := godotenv.Load("C:\\Users\\user\\people_service\\service\\configs\\config.env")
	if err != nil {
		return "", err
	}

	return os.Getenv("NAME"), nil
}
