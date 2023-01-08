package main

import (
	"log"
	"github.com/LilithCG/3254_Saityrly_TBD/2022_2/buy_list/pkg/db"
	"github.com/LilithCG/3254_Saityrly_TBD/2022_2/buy_list/internal/repository"
	"github.com/LilithCG/3254_Saityrly_TBD/2022_2/buy_list/internal/handler"
	"github.com/LilithCG/3254_Saityrly_TBD/2022_2/buy_list/internal/app"
)

func main() {
	db, err := db.Init()
	if err != nil {
		log.Fatalf(err.Error())
	} 
	repo := repository.NewRepository(db)
	hand := handler.NewHandler(repo)
	app := app.NewApp(hand)
	app.Start()
}