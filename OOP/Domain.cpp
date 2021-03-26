#include "Domain.h"

Book::Book(const std::string title, const std::string author, const std::string genre, const int year, const std::string description, const std::string cover)
{
	this->title = title;
	this->author = author;
	this->genre = genre;
	this->year = year;
	this->description = description;
	this->cover = cover;
}

Book::Book()
{
}

Book::~Book()
{
}

std::string Book::show_on_screen()
{
	std::string show = "";
	show += this->title;
	show += ", ";
	show += this->author;
	show += ", ";
	show += this->genre;
	show += ", ";
	show += std::to_string(this->year);
	show += ", ";
	show += this->description;
	show += ", ";
	show += this->cover;
	return show;
}

void Book::operator=(const Book& other)
{
	this->author = other.author;
	this->description = other.description;
	this->title = other.title;
	this->year = other.year;
	this->genre = other.genre;
	this->cover = other.cover;
}

const int Book::getYear()
{
	return this->year;
}

const std::string Book::getTitle()
{
	return this->title;
}

const std::string Book::getAuthor()
{
	return this->author;
}

const std::string Book::getGenre()
{
	return this->genre;
}

const std::string Book::getDescription()
{
	return this->description;
}

const std::string Book::getCover()
{
	return this->cover;
}

std::vector<std::string> Book::split(const std::string& line, char separator)
{
	std::vector<std::string> finalResult;
	std::stringstream buff(line);
	std::string token;

	while (getline(buff, token, separator))
		finalResult.push_back(token);
	return finalResult;
}

std::istream& operator>>(std::istream& istream, Book& book)
{
	std::string line;
	getline(istream, line);
	if (line == "")
		return istream;

	std::vector<std::string> parts = book.split(line, ',');
	book.title = parts[0];
	book.author = parts[1];
	book.genre = parts[2];
	book.year = stoi(parts[3]);
	book.description = parts[4];
	book.cover = parts[5];

	return istream;
}

std::ostream& operator<<(std::ostream& ofstream, Book& book)
{
	ofstream << book.getTitle() << "," << book.getAuthor() << "," << book.getGenre() << "," <<book.getYear() <<","<<book.getDescription()<<","<<book.getCover()<< std::endl;
	return ofstream;
}