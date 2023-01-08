package repository

import (
	"context"
	"database/sql"
	"fmt"
	"testing"

	"github.com/golang-migrate/migrate/v4"
	_ "github.com/golang-migrate/migrate/v4/source/file"
	"github.com/jmoiron/sqlx"
	"github.com/testcontainers/testcontainers-go"
	"github.com/testcontainers/testcontainers-go/wait"
)


func CreateTestDatabase() (testcontainers.Container, *sqlx.DB) {
	containerReq := testcontainers.ContainerRequest{
		Image:        "postgres:latest",
		ExposedPorts: []string{"5432/tcp"},
		WaitingFor:   wait.ForListeningPort("5432/tcp"),
		Env: map[string]string{
			"POSTGRES_DB":       "test_container",
			"POSTGRES_PASSWORD": "zxc666",
			"POSTGRES_USER":     "postgres",
		},
	}

	dbContainer, err := testcontainers.GenericContainer(
		context.Background(),
		testcontainers.GenericContainerRequest{
			ContainerRequest: containerReq,
			Started:          true,
		})
	if err != nil {
		panic(err)
	}

	host, err := dbContainer.Host(context.Background())
	if err != nil {
		panic(err)
	}
	port, err := dbContainer.MappedPort(context.Background(), "5432")
	if err != nil {
		panic(err)
	}

	connString := fmt.Sprintf("postgres://postgres:pass@%v:%v/test?sslmode=disable", host, port.Port())
	db, err := sqlx.Connect("postgres", connString)
	if err != nil {
		panic(err)
	}
	driver, err := postgres.WithInstance(db.DB, &postgres.Config{})
	if err != nil {
		panic(err)
	}

	m, err := migrate.NewWithDatabaseInstance(
		"file:../../migrations",
		"postgres", driver)
	if err != nil {
		panic(err)
	}
	m.Up()
	return dbContainer, db
}

func TestCreateUsers(t *testing.T) {
	container, conn := CreateTestDatabase()
	defer container.Terminate(context.Background())
	query := Connection{
		conn: conn,
	}
	user := model.User{
		Name: "Max",
		State: model.State{
			Text: "default",
		},
	}
	err := query.CreateUser(user)
	if err != nil {
		t.Error(err)
	}
}

func TestGetByName(t *testing.T) {
	container, conn := CreateTestDatabase()
	defer container.Terminate(context.Background())
	query := Connection{
		conn: conn,
	}
	user := model.User{
		Id: 2,
		Name: "Max",
		State: model.State{
			Text: "default"
		}
	}
	query.CreateUser(user)
	_, err := query.GetByName("Max")
	if err != nil && err != sql.ErrNoRows {
		t.Error(err)
	}
}

func TestGetStateByTextAndUser(t *testing.T) {
	container, conn := CreateTestDatabase()
	defer container.Terminate(context.Background())
	query := Connection{
		conn: conn,
	}
	user := model.User{
		Id: 3,
		Name: "Max",
		State: model.State{
			Text: "default"
		}
	}
	query.CreateUser(user)
	_, err := query.GetStateByTextAndUser(3)
	if err != nil && err != sql.ErrNoRows {
		t.Error(err)
	}
}

func TestUpdateUserState(t *testing.T) {
	container, conn := CreateTestDatabase()
	defer container.Terminate(context.Background())
	query := Connection{
		conn: conn,
	}
	user := model.User{
		Id: 3,
		Name: "Max",
		State: model.State{
			Text: "default"
		}
	}
	query.CreateUser(user)
	_, err := query.UpdateUserState(4, 1)
	if err != nil && err != sql.ErrNoRows {
		t.Error(err)
	}
}

func TestCreateProduct(t *testing.T) {
	clean(t)
	container, conn := CreateTestDatabase()
	defer container.Terminate(context.Background())
	query := Connection{
		conn: conn,
	}
	product := model.Product{
		Id: 1,
		Name: "default",
		Weight: 20.0,
		Status: model.Status{

		},
		User: model.User{
			Id: 3,
			Name: "Max",
			State: model.State{
				Text: "default"
			}
		}
	}
	err := query.CreateProduct(product)
	if err != nil {
		t.Error(err)
	}
}