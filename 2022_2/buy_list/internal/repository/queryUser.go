package repository

import (
	"github.com/LilithCG/3254_Saityrly_TBD/2022_2/buy_list/internal/model"
	"gorm.io/gorm"
)

type user struct {
	db *gorm.DB
}

func NewUser(db *gorm.DB) *user {
	return &user{db}
}

func (r *user) CreateUser(user *model.User) (int64, error) {
	res := r.db.Create(user)
	return user.ID, res.Error
}

func (r *user) GetByName(name string) (int64, error) {
	var user model.User
	res := r.db.Model(&model.User{}).First(&user, "name = ?", name)
	return user.ID, res.Error
}

func (r *user) GetStateByTextAndUser(id int64) (string, error) {
	var user model.User
	res := r.db.Model(&model.User{}).First(&user, "id = ?", id)
	return user.State.Text, res.Error
}

func (r *user) UpdateUserState(id int64, state int) error {
	res := r.db.Model(&model.User{}).Where("id = ?", id).Update("state_id", state)
	return res.Error
}
