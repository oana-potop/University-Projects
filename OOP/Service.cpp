#include "Service.h"

Service::Service(CSVRepository repository)
{
	this->repository = repository;
	this->currentIndex = 0;
	this->myPlaylist = CSVRepository{};
	this->undo_stack = std::stack<Operation>{};
	this->redo_stack = std::stack<Operation>{};
}

Service::Service(Service& other)
{
	this->repository = other.repository;
	this->currentIndex = other.currentIndex;
	this->undo_stack = other.undo_stack;
	this->redo_stack = other.redo_stack;
}

Service::Service()
{
	this->repository = CSVRepository{};
	this->currentIndex = 0;
	this->undo_stack = std::stack<Operation>{};
	this->redo_stack = std::stack<Operation>{};
}

Service::~Service()
{
}

void Service::add(const std::string title, const std::string author, const std::string genre, const int year, const std::string description, const std::string cover, bool ok)
{
	Book book_to_add{ title, author, genre, year, description, cover };
	repository.add(book_to_add);
	if (ok) {
		std::vector<std::string> params;
		params.push_back(title);
		Operation operation{ "remove", params };
		this->undo_stack.push(operation);
	}
}

void Service::add_to_playlist(const std::string title, const std::string author, const std::string genre, const int year, const std::string description, const std::string cover)
{
	Book book_to_add{ title, author, genre, year, description, cover };
	myPlaylist.add(book_to_add);
}

void Service::remove(const std::string title, bool ok)
{
	Book book_to_remove{ title, "", "", 0, "", "" };
	Book book = this->repository.get_book(title);
	repository.remove(book_to_remove);
	if (ok) {
		std::vector<std::string> params;
		params.push_back(book.getTitle());
		params.push_back(book.getAuthor());
		params.push_back(book.getGenre());
		params.push_back(std::to_string(book.getYear()));
		params.push_back(book.getDescription());
		params.push_back(book.getCover());
		Operation operation{ "add", params };
		this->undo_stack.push(operation);
	}
}

void Service::removePlaylist(const std::string title)
{
	Book book_to_remove{ title, "", "", 0, "", "" };
	myPlaylist.remove(book_to_remove);
}

void Service::update(const std::string title, const std::string author, const std::string genre, const int year, const std::string description, const std::string cover, bool ok)
{
	Book book_to_update{ title, author, genre, year, description, cover };
	Book book = this->repository.get_book(title);
	repository.update(book_to_update);
	if (ok) {
		std::vector<std::string> params;
		params.push_back(title);
		params.push_back(book.getAuthor());
		params.push_back(book.getGenre());
		params.push_back(std::to_string(book.getYear()));
		params.push_back(book.getDescription());
		params.push_back(book.getCover());
		Operation operation{ "update", params };
		this->undo_stack.push(operation);
	}
}

int Service::getLength()
{
	return this->repository.getLength();
}

int Service::getLengthPlaylist()
{
	return this->myPlaylist.getLength();
}

std::vector<Book> Service::getAll()
{
	return this->repository.getAll();
}

std::vector<Book> Service::getAllPlaylist()
{
	return this->myPlaylist.getAll();
}

bool Service::isEmpty(std::string genre)
{
	std::vector<Book> books = this->getAll();
	for (int i = 0;i <= getLength();i++)
		if (books[i].getGenre() == genre)
			return false;
	return true;
}

int Service::sizeGenre(std::string genre)
{
	std::vector<Book> books = this->getAll();
	int k = 0;
	for (int i = 0;i < this->getLength();i++)
		if (books[i].getGenre() == genre)
			k++;
	return k;
}



void Service::resetIndex()
{
	this->currentIndex = 0;
}

void Service::undo()
{
	if (this->undo_stack.empty())
		return;
	Operation current = this->undo_stack.top();
	this->undo_stack.pop();
	if (current.get_name() == "add") {
		std::vector<std::string> params = current.get_params();
		this->add(params[0], params[1], params[2], std::stoi(params[3]), params[4], params[5], false);
		std::vector<std::string> new_params{};
		new_params.push_back(params[0]);
		Operation redo_operation{ "remove", new_params };
		this->redo_stack.push(redo_operation);
	}
	else if (current.get_name() == "remove") {
		std::vector<std::string> params = current.get_params();
		Book book = this->repository.get_book(params[0]);
		this->remove(params[0], false);
		std::vector<std::string> new_params{};
		new_params.push_back(book.getTitle());
		new_params.push_back(book.getAuthor());
		new_params.push_back(book.getGenre());
		new_params.push_back(std::to_string(book.getYear()));
		new_params.push_back(book.getDescription());
		new_params.push_back(book.getCover());;
		Operation redo_operation("add", new_params);
		this->redo_stack.push(redo_operation);
	}
	else if (current.get_name() == "update") {
		std::vector<std::string> params = current.get_params();
		Book book = this->repository.get_book(params[0]);
		this->update(params[0], params[1], params[2], std::stoi(params[3]), params[4], params[5], false);
		std::vector<std::string> new_params{};
		new_params.push_back(book.getTitle());
		new_params.push_back(book.getAuthor());
		new_params.push_back(book.getGenre());
		new_params.push_back(std::to_string(book.getYear()));
		new_params.push_back(book.getDescription());
		new_params.push_back(book.getCover());;
		Operation redo_operation("update", new_params);
		this->redo_stack.push(redo_operation);
	}
}

void Service::redo()
{
	if (this->redo_stack.empty())
		return;

	Operation current = this->redo_stack.top();
	this->redo_stack.pop();
	if (current.get_name() == "add") {
		std::vector<std::string> params = current.get_params();
		this->add(params[0], params[1], params[2], std::stoi(params[3]), params[4], params[5], false);
		std::vector<std::string> new_params{};
		new_params.push_back(params[0]);
		Operation redo_operation{ "remove", new_params };
		this->undo_stack.push(redo_operation);
	}
	else if (current.get_name() == "remove") {
		std::vector<std::string> params = current.get_params();
		Book book = this->repository.get_book(params[0]);
		this->remove(params[0], false);
		std::vector<std::string> new_params{};
		new_params.push_back(book.getTitle());
		new_params.push_back(book.getAuthor());
		new_params.push_back(book.getGenre());
		new_params.push_back(std::to_string(book.getYear()));
		new_params.push_back(book.getDescription());
		new_params.push_back(book.getCover());;
		Operation redo_operation("add", new_params);
		this->undo_stack.push(redo_operation);
	}
	else if (current.get_name() == "update") {
		std::vector<std::string> params = current.get_params();
		Book book = this->repository.get_book(params[0]);
		this->update(params[0], params[1], params[2], std::stoi(params[3]), params[4], params[5], false);
		std::vector<std::string> new_params{};
		new_params.push_back(book.getTitle());
		new_params.push_back(book.getAuthor());
		new_params.push_back(book.getGenre());
		new_params.push_back(std::to_string(book.getYear()));
		new_params.push_back(book.getDescription());
		new_params.push_back(book.getCover());;
		Operation redo_operation("update", new_params);
		this->undo_stack.push(redo_operation);
	}
}
