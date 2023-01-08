package model

import (
	"time"

	"gorm.io/gorm"
)

type ProductList struct {
	gorm.Model
	ID           int64   `gorm:"primaryKey"`
	Product      Product `gorm:"embedded;embeddedPrefix:product_"`
	NotificateAt time.Time
}
