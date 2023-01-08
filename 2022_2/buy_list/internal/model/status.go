package model

import "gorm.io/gorm"

type Status struct {
	gorm.Model
	ID   int64 `gorm:"primaryKey"`
	Name string
}
