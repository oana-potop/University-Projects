#include "Repository.h"

#define error_code 1;


CSVRepository::CSVRepository(std::string path)
{
	this->path = path;
}

CSVRepository::CSVRepository()
{
}

CSVRepository::CSVRepository(const CSVRepository& other)
{
	this->path = other.path;
}

CSVRepository::~CSVRepository()
{
}

void CSVRepository::add(Book& book)
{
	std::vector<Book> elements = this->read_from_file();
	if (this->search(book) != -1)
		throw error_code;
	elements.push_back(book);
	this->write_to_file(elements);
}

void CSVRepository::remove(Book& book)
{
	std::vector<Book> elements = this->read_from_file();
	if (this->search(book) == -1)
		throw error_code;
	for (int i = 0;i < elements.size();i++)
		if (book.getTitle() == elements[i].getTitle())
			elements.erase(elements.begin() + i);
	this->write_to_file(elements);
}

void CSVRepository::update(Book& book)
{
	std::vector<Book> elements = this->read_from_file();
	if (this->search(book) == -1)
		throw error_code;
	for (int i = 0;i < elements.size();i++)
		if (book.getTitle() == elements[i].getTitle())
			elements.at(i) = book;
	this->write_to_file(elements);
}

int CSVRepository::search(Book& book)
{
	std::vector<Book> elements = this->read_from_file();
	int i;
	for (i = 0; i < elements.size(); i++)
		if (elements[i].getTitle() == book.getTitle())
			return i;
	return -1;
}

std::vector<Book> CSVRepository::getAll()
{
	return this->read_from_file();
}

int CSVRepository::getLength()
{
	std::vector<Book> elements = this->read_from_file();
	return elements.size();
}

std::vector<Book> CSVRepository::read_from_file()
{
	std::ifstream file{ this->path };
	Book book;

	std::vector<Book> elements{};

	while (file >> book) {
		elements.push_back(book);
	}

	file.close();
	return elements;
}

void CSVRepository::write_to_file(std::vector<Book> books)
{
	std::ofstream file{ this->path };
	for (auto element : books) {
		file << element;
	}
	file.close();
}