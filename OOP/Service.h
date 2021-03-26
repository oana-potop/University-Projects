#pragma once
#include "Repository.h"
#include <stack>

class Operation {
	std::string name;
	std::vector<std::string> params;
public:
	Operation() { this->name = ""; this->params = std::vector<std::string>{}; }
	Operation(std::string name, std::vector<std::string> params) {
		this->name = name; this->params = params;
	}
	Operation(const Operation& other) { this->name = other.name; this->params = other.params; }
	~Operation() {}

	std::string get_name() { return this->name; }
	std::vector<std::string> get_params() { return this->params; }
};

class Service {
private:
	CSVRepository repository;
	int currentIndex;
	CSVRepository myPlaylist;
	std::stack<Operation> undo_stack;
	std::stack<Operation> redo_stack;

public:
	Service(CSVRepository repository);
	Service(Service& other);
	Service();
	~Service();

	void add(const std::string title, const std::string author, const std::string genre, const int year, const std::string description, const std::string cover, bool ok = true);

	void add_to_playlist(const std::string title, const std::string author, const std::string genre, const int year, const std::string description, const std::string cover);
	void remove(const std::string title, bool ok = true);

	void removePlaylist(const std::string title);
	void update(const std::string title, const std::string author, const std::string genre, const int year, const std::string description, const std::string cover, bool ok = true);
	//void update(const std::string title, const std::string author, const std::string genre, const int year, const std::string description, const std::string cover);
	int getLength();
	int getLengthPlaylist();
	std::vector<Book> getAll();
	std::vector<Book> getAllPlaylist();
	//std::vector<Book> getAllGenre(std::string genre);
	bool isEmpty(std::string genre);
	int sizeGenre(std::string genre);
	void resetIndex();

	//TElem GetByGenre(const std::string genre);

	//
	void undo();
	void redo();
};