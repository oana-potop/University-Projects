#include "UserInterface.h"

#include <string>
void UserInterface::print_menu()
{
	std::cout << "\nPlease choose one option from the following :\nPress 1 to add a movie (*Note a movie has a name, a rating and some additional info in this order)\nPress 2 to remove a movie at a given position\n";
	std::cout << "Press 3 to search a movie with a given rating\nPress 4 to list all movies\nPress 5 to get the best movie by rating\nPress 6 to get movie by its position in the watchlist\nPress 7 to remove all movies with rating smaller than the one at the given position\nPress 0 to exit\n";
}

void UserInterface::print_details()
{
	std::cout << "#######################################################################################\n";
	std::cout << "Movie Application\n";
	std::cout << "This application will allow the user to keep a watchlist of his favorite movies.\n";
	std::cout << "The application will use a Sorted List on a binary search tree.\n";
	std::cout << "The application has 3 movies already added.\n";
	std::cout << "In order to have a good complexity for functions like get_best_movie we will sort the movies by their rating.\n";
	std::cout << "When printing all the movies, the application will only show the movie's name and rating.\n";
	std::cout << "But when printing a movie from a given position, the application will also show the additional information of the movie, to use the operation get_by_position of the sorted list\n";
	std::cout << "When removing the items, the application will remove all the movies with a rating lower than that of the movie at the given position.\n";
	std::cout << "For that we will use the get_position of the sorted list, we will find the first element with a rating lower than that of the given one and then call remove for all elements with lower position.\n";
	std::cout << "A problem that I want to discuss is what happens if two movies are 'equal', which is very likely to occur in our application.\n";
	std::cout << "Depeding on the relation, our bst will store two 'equal' movies (one parent and one child) on the left subtree or the right subtree of the parent ( depeding on the relation always on left or always on right!)\n";
	std::cout << "For our default relation, because it returns true if movie1 <= movie2 then it will store two equal values on the left subtree of the last one.\n";
	std::cout << "Hovewer, we will construct our bst taking in account that if adding a value that already exists, it might go to the right or left subtree of the already existing entity.\n";
	std::cout << "#######################################################################################\n";
}

void UserInterface::run()
{
	this->print_details();
	std::string command;
	while (1) {
		this->print_menu();
		int option;
		std::cin >> option;
		if (option == 1)
			this->add();
		else if (option == 2)
			this->remove();
		else if (option == 3)
			this->search();
		else if (option == 4)
			this->print();
		else if (option == 5)
			this->get_best_movie();
		else if (option == 6)
			this->get_movie_by_index();
		else if (option == 7)
			this->remove_all();
		else if (option == 0)
			return;
		else
			std::cout << "Command not found";
	}
}

void UserInterface::config()
{
	// function to populate the application
	Movie m1{ 9, "Lord of the Rings: The Fellowship of the ring ", "Amazing display of J.R.R Tolkien's book Lord of the rings, marks the beggining of an epic journey" };
	Movie m2{ 8, "Lord of the Rings: The two towers ", "Second movie of the epic triology lord of the rings, epic story, amazing characters" };
	Movie m3{ 10, "Lord of the Rings: Return of the king  ", "Third and last movie of the epic triology lord of the rings, marks the end of the story" };
	this->sl->add(m1);
	this->sl->add(m2);
	this->sl->add(m3);

}

UserInterface::UserInterface(Relation r)
{
	this->sl = new SortedList{ r };
}

UserInterface::UserInterface(const UserInterface& other)
{
	this->sl = other.sl;
}

UserInterface::~UserInterface()
{
}

void UserInterface::add()
{
	std::cin.get();
	std::string name;
	int rating;
	std::cout << "Enter name\n";
	std::getline(std::cin, name);

	std::cout << "Enter rating\n";
	std::cin >> rating;
	std::cin.get();
	std::cout << "Please enter additional info for the movie\n";
	std::string info;
	std::getline(std::cin, info);

	Movie m{ rating, name,info };
	this->sl->add(m);

}

void UserInterface::remove()
{
	int position;
	std::cout << "Please enter the position of the movie you want to delete: \n";
	std::cin >> position;
	Movie m = this->sl->getByPosition(position - 1);
	this->sl->remove(m);

}

void UserInterface::remove_all()
{
	int position;
	std::cout << "Please insert the position\n";
	std::cin >> position;
	int size = this->sl->size() - position;
	while (size > 0)
	{
		this->sl->remove(this->sl->getByPosition(position));
		size--;
	}

}

void UserInterface::search()
{
	std::cout << "Plese insert the movie rating \n";
	int rating;
	std::cin >> rating;

	Movie dummy_movie{ rating, "", "NOTHING" };
	if (this->sl->search(dummy_movie))
		std::cout << "We have a movie with the given rating\n";
	else
		std::cout << "We do not have a movie with the given rating\n";
}

void UserInterface::print()
{
	ListIterator it = this->sl->iterator();
	int position = 0;
	while (it.valid()) {
		std::cout << "Pos: " << ++position << " ";
		std::cout << it.getCurrent().print() << "\n";
		it.next();
	}
	std::cout << "\n\n";
}

void UserInterface::get_best_movie()
{
	std::cout << this->sl->getByPosition(0).print_extended() << std::endl;
}

void UserInterface::get_movie_by_index()
{
	int position;
	std::cout << "Please insert the position:\n";
	std::cin >> position;
	std::cout << this->sl->getByPosition(position - 1).print_extended() << std::endl;
}

