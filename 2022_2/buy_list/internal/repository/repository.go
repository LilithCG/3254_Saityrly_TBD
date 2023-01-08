package repository

import (
	"github.com/LilithCG/3254_Saityrly_TBD/2022_2/buy_list/internal/model"
	"gorm.io/gorm"
)

//go:generate mockgen -source=repository.go -destination=mocks/mock.go
type User interface {
	CreateUser(user *model.User) (int64, error)
	GetByName(ref string) (int64, error)
	GetStateByTextAndUser(id int64) (string, error)
	UpdateUserState(id int64, state int) error
}

type Status interface {
	GetStatusById(id int) (*model.Status, error)
}

type Product interface {
	CreateProduct(product *model.Product) (int64, error)
	UpdateStatusProduct(id int64, status *model.Status) error
	GetProductById(id int64) (*model.Product, error)
}

type ProductList interface {
	CreateProductList(productList *model.ProductList) (int64, error)
	DeleteProductList(id int64) error
	GetProductListById(id int64) (*model.ProductList, error)
	GetAllproducts() ([]model.Product, error)
}

type Fridge interface {
	CreateFridge(fridge *model.Fridge) (int64, error)
	UpdateDieDayFridge(id int64, day int) error
	GetFridgeById(id int64) (*model.Fridge, error)
	GetAllproducts() ([]model.Fridge, error)
}

type Repository struct {
	User
	Status
	Product
	ProductList
	Fridge
}

func NewRepository(db *gorm.DB) *Repository {
	return &Repository{
		User:        NewUser(db),
		Status:      NewStatus(db),
		Product:     NewProduct(db),
		ProductList: NewProductList(db),
		Fridge:      NewFridge(db),
	}
}
