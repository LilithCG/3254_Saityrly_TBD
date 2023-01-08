package model

import "gorm.io/gorm"

type Fridge struct {
	gorm.Model
	ID      int64   `gorm:"primaryKey"`
	Product Product `gorm:"embedded;embeddedPrefix:product_"`
	DieDay  int
}
