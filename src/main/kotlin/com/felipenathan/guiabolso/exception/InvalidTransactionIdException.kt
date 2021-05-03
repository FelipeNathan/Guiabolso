package com.felipenathan.guiabolso.exception

class InvalidTransactionIdException :
    GuiabolsoException("Id inválido, id está fora do intervalo permitido. Intervalo 1.000 à 100.000")