#include <iostream>
#include <vector>
#include <sstream>
class Book {
private:
	int year;
	std::string title;
	std::string author;
	std::string genre;
	std::string description;
	std::string cover;
public:
	Book(const std::string title, const std::string author, const std::string genre, const int year, const std::string description, const std::string cover);
	Book();
	~Book();
	std::string show_on_screen();

	//GETTERS
	void operator=(const Book& other);
	std::vector<std::string> split(const std::string& line, char separator);
	friend std::istream& operator>>(std::istream& istream, Book& book);
	friend std::ostream& operator<<(std::ostream& ofstream, Book& book);
	const int getYear();
	const std::string getTitle();
	const std::string getAuthor();
	const std::string getGenre();
	const std::string getDescription();
	const std::string getCover();
};