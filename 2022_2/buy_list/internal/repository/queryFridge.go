package repository

import (
	"github.com/LilithCG/3254_Saityrly_TBD/2022_2/buy_list/internal/model"
	"gorm.io/gorm"
)

type fridge struct {
	db *gorm.DB
}

func NewFridge(db *gorm.DB) *fridge {
	return &fridge{db}
}

func (r *fridge) CreateFridge(fridge *model.Fridge) (int64, error) {
	res := r.db.Create(fridge)
	return fridge.ID, res.Error
}

func (r *fridge) UpdateDieDayFridge(id int64, day int) error {
	res := r.db.Model(&model.Fridge{}).Where("id = ?", id).Update("day", day)
	return res.Error
}

func (r *fridge) GetFridgeById(id int64) (*model.Fridge, error) {
	var fridge model.Fridge
	res := r.db.Model(&model.Fridge{}).First(&fridge, "id = ?", id)
	return &fridge, res.Error
}

func (r *fridge) GetAllproducts() ([]model.Fridge, error) {
	var products []model.Fridge
	res := r.db.Find(&products)
	return products, res.Error
}