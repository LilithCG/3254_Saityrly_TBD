package model

type User struct {
	ID    int64 `gorm:"primaryKey"`
	Name  string
	State State
}
