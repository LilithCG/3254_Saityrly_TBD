package model

import "gorm.io/gorm"

type Product struct {
	gorm.Model
	ID     int64 `gorm:"primaryKey"`
	Name   string
	Weight float32
	Status Status `gorm:"embedded;embeddedPrefix:status_"`
	User   User   `gorm:"embedded;embeddedPrefix:user_"`
}
