package repository

import (
	"github.com/LilithCG/3254_Saityrly_TBD/2022_2/buy_list/internal/model"
	"gorm.io/gorm"
)

type productList struct {
	db *gorm.DB
}

func NewProductList(db *gorm.DB) *productList {
	return &productList{db}
}

func (r *productList) CreateProductList(productList *model.ProductList) (int64, error) {
	res := r.db.Create(productList)
	return productList.ID, res.Error
}

func (r *productList) DeleteProductList(id int64) error {
	var productList model.ProductList
	res := r.db.Model(&model.ProductList{}).Where("id = ?", id).Delete(&productList)
	return res.Error
}

func (r *productList) GetProductListById(id int64) (*model.ProductList, error) {
	var productList model.ProductList
	res := r.db.Model(&model.ProductList{}).First(&productList, "id = ?", id)
	return &productList, res.Error
}

func (r *productList) GetAllproducts() ([]model.Product, error) {
	var products []model.Product
	res := r.db.Find(&products)
	return products, res.Error
}
