#include <iostream>
#include <string>

class Movie {
private:
	int rating;
	std::string name;
	std::string additional_info;

public:
	Movie(int rating, std::string name, std::string additional_info) {
		this->rating = rating; this->name = name; this->additional_info = additional_info;
	}
	Movie() { this->rating = -1; this->name = ""; this->additional_info = ""; }
	Movie(const Movie& other) { this->rating = other.rating; this->name = other.name; this->additional_info = other.additional_info; }
	~Movie() {}

	std::string print() {
		std::string print = "";
		print += this->name;
		print += " Movie rating ";
		print += std::to_string(this->rating);
		return print;
	}
	std::string print_extended() {
		std::string print = "Movie name: ";
		print += this->name;
		print += "\n";
		print += "Movie information: ";
		print += this->additional_info;
		return print;
	}

	//std::string get_name() { return this->name; }
	int get_rating() { return this->rating; } // FOR TESTING ONLY

	bool operator==(const Movie& other) {
		return this->rating == other.rating;
	}
	bool operator>=(const Movie& other) {
		return this->rating >= other.rating;
	}
	bool operator>(const Movie& other) {
		return this->rating > other.rating;
	}
	bool operator <= (const Movie& other) {
		return this->rating <= other.rating;
	}
	bool operator < (const Movie& other) {
		return this->rating < other.rating;
	}
	bool operator !=(const Movie& other) {
		return this->rating != other.rating;
	}
};