package handler

import (
	"fmt"
	"log"
	"strconv"

	"github.com/LilithCG/3254_Saityrly_TBD/2022_2/buy_list/internal/model"
	"github.com/LilithCG/3254_Saityrly_TBD/2022_2/buy_list/internal/repository"
	tgbotapi "github.com/go-telegram-bot-api/telegram-bot-api/v5"
)

type Handler struct {
	Repo *repository.Repository
}

func NewHandler(repo *repository.Repository) *Handler {
	return &Handler{
		Repo: repo,
	}
}

func (h *Handler) Start(id int64, name string) error {
	user := &model.User{ID: id, Name: name}
	userID, err := h.Repo.User.CreateUser(user)

	if err != nil {
		return err
	}
	log.Printf("Success added user with id = %d", userID)
	return nil
}

func (h *Handler) GetProductList() tgbotapi.InlineKeyboardMarkup {
	return tgbotapi.NewInlineKeyboardMarkup(
		tgbotapi.NewInlineKeyboardRow(
			tgbotapi.NewInlineKeyboardButtonData("Add to Product List", "add_to_list_product"),
			tgbotapi.NewInlineKeyboardButtonData("See Product List", "see_list_product"),
		),
	)
}

func (h *Handler) AddToProdList(id int64) error {
	if err := h.Repo.User.UpdateUserState(id, 2); err != nil {
		return err
	}
	return nil
}

func (h *Handler) SetProductName(id int64) error {
	if err := h.Repo.User.UpdateUserState(id, 3); err != nil {
		return err
	}
	return nil
}

func (h *Handler) SetProductWeight(id int64) error {
	if err := h.Repo.User.UpdateUserState(id, 4); err != nil {
		return err
	}
	return nil
}

func (h *Handler) SetProductNotify(id int64, model *model.ProductList) (string, error) {
	if err := h.Repo.User.UpdateUserState(id, 1); err != nil {
		return "", err
	}

	_, err := h.Repo.ProductList.CreateProductList(model)
	if err != nil {
		return "", err
	}

	return "Success set notifier and add product", nil
}

func (h *Handler) SeeProductList(msg tgbotapi.MessageConfig, bot *tgbotapi.BotAPI) {
	prods, err := h.Repo.ProductList.GetAllproducts()
	if err != nil {
		msg.Text = "Some Error! Please try again!"
		bot.Send(msg)
	}
	for _, p := range prods {
		id := fmt.Sprintf("%d", p.ID)
		msg.Text = p.Name
		msg.ReplyMarkup = tgbotapi.NewInlineKeyboardMarkup(
			tgbotapi.NewInlineKeyboardRow(
				tgbotapi.NewInlineKeyboardButtonData("Delete from list", "delete-list"+id),
				tgbotapi.NewInlineKeyboardButtonData("To fridge", "add-fridge"+id),
			))
		bot.Send(msg)
	}
}

func (h *Handler) DeleteFromList(id int64) (string, error) {
	if err := h.Repo.ProductList.DeleteProductList(id); err != nil {
		return "", err
	}
	return "Success delete", nil
}

func (h *Handler) ToFridge(id int64) (string, error) {
	product, err := h.Repo.Product.GetProductById(id)
	if err != nil {
		return "", err
	}
	_, err = h.Repo.Fridge.CreateFridge(&model.Fridge{Product: *product})
	if err != nil {
		return "", err
	}
	return "Success move to fridge", nil
}

func (h *Handler) SetDieDate(id int64, prodId string, date string) error {
	i, err := strconv.ParseInt(prodId, 10, 64)
	if err != nil {
		log.Printf(err.Error())
	}
	d, err := strconv.ParseInt(date, 10, 32)
	if err != nil {
		log.Printf(err.Error())
	}

	if err := h.Repo.Fridge.UpdateDieDayFridge(i, int(d)); err != nil {
		return err
	}
	if err := h.Repo.User.UpdateUserState(id, 1); err != nil {
		return err
	}
	return nil
}

func (h *Handler) OpenFridge(msg tgbotapi.MessageConfig, bot *tgbotapi.BotAPI) {
	prods, err := h.Repo.Fridge.GetAllproducts()
	if err != nil {
		msg.Text = "Some Error! Please try again!"
		bot.Send(msg)
	}
	for _, p := range prods {
		id := fmt.Sprintf("%d", p.ID)
		msg.Text = p.Product.Name
		msg.ReplyMarkup = tgbotapi.NewInlineKeyboardMarkup(
			tgbotapi.NewInlineKeyboardRow(
				tgbotapi.NewInlineKeyboardButtonData("Open", "open"+id),
				tgbotapi.NewInlineKeyboardButtonData("Drop", "drop"+id),
				tgbotapi.NewInlineKeyboardButtonData("Cook", "cook"+id),
			))
		bot.Send(msg)
	}
}

func (h *Handler) Open(id int64) error {
	var status *model.Status
	var err error
	if status, err = h.Repo.Status.GetStatusById(1); err != nil {
		return err
	}
	if err = h.Repo.Product.UpdateStatusProduct(id, status); err != nil {
		return err
	}
	if err = h.Repo.User.UpdateUserState(id, 6); err != nil {
		return err
	}
	return nil
}

func (h *Handler) Drop(id int64) error {
	var status *model.Status
	var err error
	if status, err = h.Repo.Status.GetStatusById(2); err != nil {
		return err
	}
	if err = h.Repo.Product.UpdateStatusProduct(id, status); err != nil {
		return err
	}
	return nil
}

func (h *Handler) Cook(id int64) error {
	var status *model.Status
	var err error
	if status, err = h.Repo.Status.GetStatusById(3); err != nil {
		return err
	}
	if err = h.Repo.Product.UpdateStatusProduct(id, status); err != nil {
		return err
	}
	return nil
}
