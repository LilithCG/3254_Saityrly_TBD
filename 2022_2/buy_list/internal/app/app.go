package app

import (
	"log"
	"os"
	"strconv"
	"strings"
	"time"

	"github.com/LilithCG/3254_Saityrly_TBD/2022_2/buy_list/internal/handler"
	"github.com/LilithCG/3254_Saityrly_TBD/2022_2/buy_list/internal/model"
	tgbotapi "github.com/go-telegram-bot-api/telegram-bot-api/v5"
)

type App struct {
	Handler *handler.Handler
}

func NewApp(hand *handler.Handler) *App {
	return &App{
		Handler: hand,
	}
}

var numericKeyboard = tgbotapi.NewReplyKeyboard(
	tgbotapi.NewKeyboardButtonRow(
		tgbotapi.NewKeyboardButton("Product List"),
		tgbotapi.NewKeyboardButton("Fridge"),
	),
)

func (a *App) Start() {
	bot, err := tgbotapi.NewBotAPI(os.Getenv("TELEGRAM_APITOKEN"))
	if err != nil {
		log.Panic(err)
	}

	bot.Debug = true

	log.Printf("Authorized on account %s", bot.Self.UserName)

	u := tgbotapi.NewUpdate(0)
	u.Timeout = 60

	var prod model.Product
	var prodList model.ProductList

	updates := bot.GetUpdatesChan(u)

	for update := range updates {
		if update.Message != nil {
			state, err := a.Handler.Repo.User.GetStateByTextAndUser(update.Message.Chat.ID)
			if err != nil {
				log.Printf(err.Error())
			}
			msg := tgbotapi.NewMessage(update.Message.Chat.ID, update.Message.Text)

			switch state {
			case "default":
				break
			case "ProductName":
				if err := a.Handler.SetProductName(update.Message.Chat.ID); err != nil {
					log.Printf(err.Error())
				}
				prod.Name = update.Message.Text
			case "ProductWeight":
				if err := a.Handler.SetProductWeight(update.Message.Chat.ID); err != nil {
					log.Printf(err.Error())
				}
				if f, err := strconv.ParseFloat(update.Message.Text, 32); err == nil {
					prod.Weight = float32(f)
				}
			case "SetNatificateDateTime":
				date, err := time.Parse("2006-01-02", update.Message.Text)
				if err != nil {
					log.Printf(err.Error())
					break
				}
				prodList.Product = prod
				prodList.NotificateAt = date
				str, err := a.Handler.SetProductNotify(update.Message.Chat.ID, &prodList)
				if err != nil {
					log.Printf(err.Error())
				}
				msg.Text = str
			case "SetDiedate":
				if err := a.Handler.SetDieDate(update.Message.Chat.ID, update.Message.Text, update.Message.Text); err != nil {
					log.Printf(err.Error())
				}
			case "SetNewDieDate":
				if err := a.Handler.SetDieDate(update.Message.Chat.ID, update.Message.Text, update.Message.Text); err != nil {
					log.Printf(err.Error())
				}
			}

			switch update.Message.Text {
			case "/start":
				if err := a.Handler.Start(update.Message.Chat.ID, update.Message.From.UserName); err != nil {
					log.Printf(err.Error())
					msg.Text = "Some error! Please try again!"
				}
			case "Product List":
				msg.Text = "Product List"
				msg.ReplyMarkup = a.Handler.GetProductList()
			case "Fridge":
				a.Handler.OpenFridge(msg, bot)
			}

			if _, err = bot.Send(msg); err != nil {
				panic(err)
			}
		} else if update.CallbackQuery != nil {
			msg := tgbotapi.NewMessage(update.CallbackQuery.Message.Chat.ID, update.CallbackQuery.Data)

			switch {
			case strings.Contains(update.CallbackQuery.Data, "add_to_list_product"):
				if err := a.Handler.AddToProdList(update.CallbackQuery.Message.Chat.ID); err != nil {
					log.Printf(err.Error())
				}
			case strings.Contains(update.CallbackQuery.Data, "see_list_product"):
				a.Handler.SeeProductList(msg, bot)
			case strings.Contains(update.CallbackQuery.Data, "delete-list"):
				val := strings.TrimPrefix(update.CallbackQuery.Data, "delete-list")
				i, err := strconv.ParseInt(val, 10, 64)
				if err != nil {
					log.Printf(err.Error())
				}
				a.Handler.DeleteFromList(i)
			case strings.Contains(update.CallbackQuery.Data, "add-fridge"):
				val := strings.TrimPrefix(update.CallbackQuery.Data, "add-fridge")
				i, err := strconv.ParseInt(val, 10, 64)
				if err != nil {
					log.Printf(err.Error())
				}
				a.Handler.ToFridge(i)
			case strings.Contains(update.CallbackQuery.Data, "open"):
				val := strings.TrimPrefix(update.CallbackQuery.Data, "open")
				i, err := strconv.ParseInt(val, 10, 64)
				if err != nil {
					log.Printf(err.Error())
				}
				a.Handler.Open(i)
				msg.Text = string(i)
			case strings.Contains(update.CallbackQuery.Data, "drop"):
				val := strings.TrimPrefix(update.CallbackQuery.Data, "drop")
				i, err := strconv.ParseInt(val, 10, 64)
				if err != nil {
					log.Printf(err.Error())
				}
				a.Handler.Drop(i)
			case strings.Contains(update.CallbackQuery.Data, "cook"):
				val := strings.TrimPrefix(update.CallbackQuery.Data, "cook")
				i, err := strconv.ParseInt(val, 10, 64)
				if err != nil {
					log.Printf(err.Error())
				}
				a.Handler.Cook(i)
			}

			if _, err := bot.Send(msg); err != nil {
				panic(err)
			}
		}
	}

}
