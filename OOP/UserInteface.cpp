#include "UserInteface.h"

UserInterface::UserInterface(Service service, QWidget* parent)
{
	this->service = service;
	Book book1{ "Harry Potter", "J.K.ROWLING", "Fantasy", 2000, "kjfskjfsd", "dads" };
	this->fake = std::vector<Book>{};
	this->fake.push_back(book1);

	this->init_graphic();
	this->connect();
	this->populate_main_list();
}

void UserInterface::connect()
{
	// connect buttons
	QObject::connect(this->add_button, &QPushButton::clicked, this, &UserInterface::add_button_handler);
	QObject::connect(this->remove_button, &QPushButton::clicked, this, &UserInterface::remove_button_handler);
	QObject::connect(this->update_button, &QPushButton::clicked, this, &UserInterface::update_button_handler);
	// connect signals and slots
	QObject::connect(this, &UserInterface::add_signal, this, &UserInterface::add);
	QObject::connect(this, &UserInterface::remove_signal, this, &UserInterface::remove);
	QObject::connect(this, &UserInterface::update_signal, this, &UserInterface::update);
	QObject::connect(this, &UserInterface::populate_signal, this, &UserInterface::populate_main_list);

}

void UserInterface::init_shortcuts()
{
	QShortcut* shortcut_undo = new QShortcut{ QKeySequence("Ctrl+x"), this };
	QShortcut* shortcut_redo = new QShortcut{ QKeySequence("Ctrl+z"), this };
	QObject::connect(shortcut_undo, &QShortcut::activated, this, &UserInterface::undo);
	QObject::connect(shortcut_redo, &QShortcut::activated, this, &UserInterface::redo);


}
void UserInterface::init_graphic()
{
	QHBoxLayout* main_layout = new QHBoxLayout{ this };
	this->init_lists(main_layout);
	this->init_buttons(main_layout);
	this->init_edits(main_layout);
	this->init_shortcuts();
}

void UserInterface::init_lists(QHBoxLayout* main_layout)
{
	this->main_list = new QListWidget{};
	this->main_list->setSelectionMode(QAbstractItemView::SingleSelection);
	main_layout->addWidget(this->main_list);

	this->error_box = new QLabel{};
	main_layout->addWidget(this->error_box);
}

void UserInterface::init_buttons(QHBoxLayout* main_layout)
{
	QWidget* button_widget = new QWidget{};
	QVBoxLayout* button_layout = new QVBoxLayout{ button_widget };

	this->add_button = new QPushButton{ "Add" };
	button_layout->addWidget(this->add_button);

	this->remove_button = new QPushButton{ "Remove" };
	button_layout->addWidget(this->remove_button);

	this->update_button = new QPushButton{ "Update" };
	button_layout->addWidget(this->update_button);

	main_layout->addWidget(button_widget);

}

void UserInterface::init_edits(QHBoxLayout* main_layout)
{
	QWidget* editWidget = new QWidget{};
	QFormLayout* editLayout = new QFormLayout{ editWidget };

	this->title_edit = new QLineEdit{};
	QLabel* title = new QLabel{ "&Title" };
	title->setBuddy(this->title_edit);

	this->author_edit = new QLineEdit{};
	QLabel* author = new QLabel{ "&Author" };
	author->setBuddy(this->author_edit);

	this->genre_edit = new QLineEdit{};
	QLabel* genre = new QLabel{ "&Genre" };
	genre->setBuddy(this->genre_edit);

	this->year_edit = new QLineEdit{};
	QLabel* year = new QLabel{ "&Year" };
	year->setBuddy(this->year_edit);

	this->description_edit = new QLineEdit{};
	QLabel* description = new QLabel{ "&Description" };
	description->setBuddy(this->description_edit);

	this->cover_edit = new QLineEdit{};
	QLabel* cover = new QLabel{ "&Cover" };
	cover->setBuddy(this->cover_edit);

	editLayout->addRow(title, this->title_edit);
	editLayout->addRow(author, this->author_edit);
	editLayout->addRow(genre, this->genre_edit);
	editLayout->addRow(year, this->year_edit);
	editLayout->addRow(description, this->description_edit);
	editLayout->addRow(cover, this->cover_edit);

	main_layout->addWidget(editWidget);
}

void UserInterface::populate_main_list()
{
	if (this->main_list->count() > 0)
		this->main_list->clear();
	std::vector<Book> elements = this->service.getAll();
	for (auto element : elements) {
		QString element_string = QString::fromStdString(element.show_on_screen());
		QListWidgetItem* element_item = new QListWidgetItem{ element_string };
		this->main_list->addItem(element_item);
	}
}

void UserInterface::add_button_handler()
{
	qInfo() << " sunt in button handler";

	QString title = this->title_edit->text();
	QString autohr = this->author_edit->text();
	QString genre = this->genre_edit->text();
	QString year = this->year_edit->text();
	QString description = this->description_edit->text();
	QString cover = this->cover_edit->text();

	emit this->add_signal(title.toStdString(), autohr.toStdString(), genre.toStdString(), stoi(year.toStdString()), description.toStdString(), cover.toStdString());
}

void UserInterface::remove_button_handler()
{
	QString title = this->title_edit->text();
	emit this->remove_signal(title.toStdString());
}

void UserInterface::update_button_handler()
{
	QString title = this->title_edit->text();
	QString autohr = this->author_edit->text();
	QString genre = this->genre_edit->text();
	QString year = this->year_edit->text();
	QString description = this->description_edit->text();
	QString cover = this->cover_edit->text();

	emit this->update_signal(title.toStdString(), autohr.toStdString(), genre.toStdString(), stoi(year.toStdString()), description.toStdString(), cover.toStdString());
}

void UserInterface::add(std::string title, std::string author, std::string genre, int year, std::string description, std::string cover) {

	qInfo() << "sunt in add";
	clear_error_box();
	try {
		this->service.add(title, author, genre, year, description, cover);
	}
	catch (...) {
		this->error_box->setText("Already in !");
	}
	emit this->populate_signal();
}


void UserInterface::remove(std::string title)
{
	clear_error_box();

	try {
		this->service.remove(title);
	}
	catch (...) {
		this->error_box->setText("Not in !");

	}

	emit this->populate_signal();
}

void UserInterface::update(std::string title, std::string author, std::string genre, int year, std::string description, std::string cover)
{
	clear_error_box();

	try {
		this->service.update(title, author, genre, year, description, cover);
	}
	catch (...) {
		this->error_box->setText("Not in !");
	}
	emit this->populate_signal();
}

void UserInterface::undo() {
	this->service.undo();
	emit this->populate_main_list();
}

void UserInterface::redo() {
	this->service.redo();
	emit this->populate_main_list();
}
