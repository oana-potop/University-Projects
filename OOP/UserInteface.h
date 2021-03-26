#pragma once
#include <QtWidgets/QApplication>
#include <qwidget.h>
#include <QDebug>
#include <QListWidget>
#include <QFormLayout>
#include <QLineEdit>
#include <qshortcut.h>
#include <QTextEdit>
#include <QPushButton>
#include <QLabel>
#include <iostream>
#include <vector>
#include "Service.h"



class UserInterface :public QWidget {
	Q_OBJECT
private:
	Service service;
	std::vector<Book> fake;

	// graphic objects
	// lists
	QListWidget* main_list;
	QLabel* error_box;
	// buttons
	QPushButton* add_button;
	QPushButton* remove_button;
	QPushButton* update_button;
	// entries
	QLineEdit* title_edit;
	QLineEdit* author_edit;
	QLineEdit* genre_edit;
	QLineEdit* year_edit;
	QLineEdit* description_edit;
	QLineEdit* cover_edit;

public:
	UserInterface(Service service, QWidget* parent = 0);
	UserInterface() {
		this->service = Service{};
	}
	UserInterface(const UserInterface& other) { this->service = other.service; }
	~UserInterface() {}

private:
	void init_graphic();
	void init_lists(QHBoxLayout* main_layout);
	void init_buttons(QHBoxLayout* main_layout);
	void init_edits(QHBoxLayout* main_layout);
	void connect();
	void init_shortcuts();
	void clear_error_box() {
		this->error_box->setText("");
	}
	//handlers
private:
	void populate_main_list();
	void add_button_handler();
	void remove_button_handler();
	void update_button_handler();
signals:
	void add_signal(std::string title, std::string author, std::string genre, int year, std::string description, std::string cover);
	void remove_signal(std::string title);
	void update_signal(std::string title, std::string author, std::string genre, int year, std::string description, std::string cover);
signals:
	void populate_signal();

public slots:
	void add(std::string title, std::string author, std::string genre, int year, std::string description, std::string cover);
	void remove(std::string title);
	void update(std::string title, std::string author, std::string genre, int year, std::string description, std::string cover);
public slots:
	void undo();
public slots:
	void redo();
};


