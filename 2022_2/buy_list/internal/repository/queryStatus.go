package repository

import (
	"github.com/LilithCG/3254_Saityrly_TBD/2022_2/buy_list/internal/model"
	"gorm.io/gorm"
)

type status struct {
	db *gorm.DB
}

func NewStatus(db *gorm.DB) *status {
	return &status{db}
}

func (r *status) GetStatusById(id int) (*model.Status, error) {
	var status model.Status
	res := r.db.Model(&model.Status{}).First(&status, "id = ?", id)
	return &status, res.Error
}
