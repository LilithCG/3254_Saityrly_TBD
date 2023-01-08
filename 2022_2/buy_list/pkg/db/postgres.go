package db

import (
	"fmt"
	"os"

	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

func Init(entities ...interface{}) (*gorm.DB, error) {
	con, err := getEnv()
	if err != nil {
		return nil, err
	}

	conn, err := connect(con)
	if err != nil {
		return nil, err
	}

	if err = migrate(conn, entities); err != nil {
		return nil, err
	}

	return conn, nil
}

func getEnv() (string, error) {
	connectionString := fmt.Sprintf("host=%s user=%s password=%s dbname=%s port=%s sslmode=disable",
		os.Getenv("DATABASE_HOST"),
		os.Getenv("POSTGRES_USER"),
		os.Getenv("POSTGRES_PASSWORD"),
		os.Getenv("POSTGRES_DB"),
		os.Getenv("DATABASE_PORT"),
	)

	return connectionString, nil
}

func connect(con string) (*gorm.DB, error) {
	db, err := gorm.Open(postgres.Open(con), &gorm.Config{})
	if err != nil {
		return nil, err
	}
	return db, nil
}

func migrate(conn *gorm.DB, entities []interface{}) error {
	return conn.AutoMigrate(entities...)
}
