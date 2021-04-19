#pragma once
#include <vector>
using std::vector;
#include <sstream>
using std::stringstream;
#include <string>
using std::string;

class HugeInteger {
private:
    vector<int> digits;

public:

  HugeInteger();
  HugeInteger(const HugeInteger & other);
  HugeInteger(const string & s);
  bool operator==(const HugeInteger & rightSide);
  const HugeInteger operator+(const HugeInteger & rightSide);
  bool operator>(const HugeInteger & rightSide);
  bool isZero() const;
  string toString() const;

};
