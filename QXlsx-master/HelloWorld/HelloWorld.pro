# HelloWorld.pro

TARGET = HelloWorld
TEMPLATE = app

QT += core

CONFIG += console
CONFIG -= app_bundle

DEFINES += QT_DEPRECATED_WARNINGS

##########################################################################
# NOTE: You can fix value of QXlsx path of source code.
#  QXLSX_PARENTPATH=./
#  QXLSX_HEADERPATH=./header/
#  QXLSX_SOURCEPATH=./source/
include(../QXlsx/QXlsx.pri)

SOURCES += main.cpp \
    mainwindow.cpp

FORMS += \
    scrollarea.ui

HEADERS += \
    mainwindow.h
