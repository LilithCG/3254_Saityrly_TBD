package repository

import (
	"github.com/LilithCG/3254_Saityrly_TBD/2022_2/buy_list/internal/model"
	"gorm.io/gorm"
)

type product struct {
	db *gorm.DB
}

func NewProduct(db *gorm.DB) *product {
	return &product{db}
}

func (r *product) CreateProduct(product *model.Product) (int64, error) {
	res := r.db.Create(product)
	return product.ID, res.Error
}

func (r *product) UpdateStatusProduct(id int64, status *model.Status) error {
	res := r.db.Model(&model.Product{}).Where("id = ?", id).Update("status", status)
	return res.Error
}

func (r *product) GetProductById(id int64) (*model.Product, error) {
	var product model.Product
	res := r.db.Model(&model.Product{}).First(&product, "id = ?", id)
	return &product, res.Error
}
