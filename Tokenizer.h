/*
 * tokenizer.h
 *
 *      Author: riedell
 */

#ifndef TOKENIZER__H_
#define TOKENIZER__H_

#include <vector>
#include <string>
#include <map>

class Tokenizer {
public:
static void tokenize(const std::string &str, std::vector<std::string> &tokens, const char delim = ' ')
{
    size_t beg = 0;
    size_t end = str.find_first_of(delim, beg);

    while (end != std::string::npos) {
        if (beg == end)
            tokens.push_back("");
        else
            tokens.push_back(str.substr(beg, end-beg));
        beg = end + 1;
        end = str.find_first_of(delim, beg);
    }

    if (beg != end)
        tokens.push_back(str.substr(beg, end-beg));
}

static void tokenize(const std::string& str, std::vector<std::string>& result,
		const std::string& delimiters, const std::string& delimiters_preserve = "",
		const std::string& quote = "", const std::string& esc = "")
{
if ( false == result.empty() ) {
		result.clear();
	}

	std::string::size_type pos = 0;
	char ch = 0;
	char delimiter = 0;
	// will be added to the tokens if the delimiter
	// is preserved
	char current_quote = 0; // the char of the current open quote
	bool quoted = false; // indicator if there is an open quote
	std::string token; // string buffer for the token
	bool token_complete = false; // indicates if the current token is
	// read to be added to the result vector
	std::string::size_type len = str.length(); // length of the input-string

	while (len > pos ) {
		ch = str.at(pos);
		delimiter = 0;

		bool add_char = true;

		bool escaped = false; // indicates if the next char is protected
		if ( false == esc.empty() ) {
			if (std::string::npos != esc.find_first_of(ch) ) {
				++pos;
				if (pos < len ) {
					ch = str.at(pos);

					add_char = true;
				} else {
					// don't add the esc-char
					add_char = false;
				}

				// ignore the remaining delimiter checks
				escaped = true;
			}
		}

		if ( false == quote.empty() && false == escaped ) {
			if (std::string::npos != quote.find_first_of(ch) ) {
				if ( false == quoted ) {
					quoted = true;
					current_quote = ch;

					add_char = false;
				} else {
					if (current_quote == ch ) {
						quoted = false;
						current_quote = 0;
						add_char = false;
					}
				} // else
			}
		}

		if ( false == delimiters.empty() && false == escaped &&false == quoted ) {
			if (std::string::npos != delimiters.find_first_of(ch) ) {
				if ( false == token.empty() ) // BUGFIX: 2006-03-04
				{
					token_complete = true;
				}
				add_char = false;
			}
		}

		bool add_delimiter = false;
		if ( false == delimiters_preserve.empty() && false == escaped &&false
				== quoted ) {
			if (std::string::npos != delimiters_preserve.find_first_of(ch) ) {
				if ( false == token.empty() ) {
					token_complete = true;
				}

				add_char = false;
				delimiter = ch;
				add_delimiter = true;
			}
		}

		if ( true == add_char ) {
			token.push_back(ch );
		}

		if ( true == token_complete && false == token.empty() ) {
			result.push_back(token );

			token.clear();

			token_complete = false;
		}

		if ( true == add_delimiter ) {
			std::string delim_token;
			delim_token.push_back(delimiter );
			result.push_back(delim_token );

		}

		++pos;
	} // while

	if ( false == token.empty() ) {
		result.push_back(token );
	}
}

typedef std::map<std::string, size_t> token_count_map_t;
static void tokenize(const std::string &str, token_count_map_t &tokens, const char delim = ' ')
{
    size_t beg = 0;
    size_t end = str.find_first_of(delim, beg);
    std::string token;

    while (end != std::string::npos) {
        token = "";
        if (beg != end)
            token = str.substr(beg, end-beg);
        beg = end + 1;
        end = str.find_first_of(delim, beg);
        if (tokens.find(token) == tokens.end())
            tokens.insert(make_pair(token, 1));
        else
            tokens[token]++;
    }

    if (beg != end) {
        token = str.substr(beg, end-beg);
        if (tokens.find(token) == tokens.end())
            tokens.insert(make_pair(token, 1));
        else
            tokens[token]++;
    }
}





};

#endif
