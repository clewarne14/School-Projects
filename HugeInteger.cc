#include "HugeInteger.h"
#include <math.h>
#include <iostream>
#include <stdio.h>
#include <vector>
using std::vector;
#include <sstream>
using std::stringstream;
#include <string>
using std::string;

//Initializes HugeInteger to zero
HugeInteger::HugeInteger() {digits.push_back(0);};

//Makes a copy of HugeInteger
HugeInteger::HugeInteger(const HugeInteger & other) {
  for(int i = 0; i <= other.digits.size(); i++){
      digits.push_back(other.digits[i]);
  }
}

//Initializes HugeInteger to value contained int the string
HugeInteger::HugeInteger(const string & s) {
  for(int i = 0; i<s.length(); i++) {
    //Checks if the character is a number and that it would not be a leading zero
    if(s[i] >= '0' && s[i] <= '9' && !(digits.size() == 0 && s[i] == '0')) {
      int x = s[i] - '0';
      digits.insert(digits.begin(), x);

    }
  }
  //If there is nothing in the vector, it pushes a 0 onto it
  if(digits.empty()) {
    digits.push_back(0);
  }
}

//Overloads the == operator so that it works with the HugeIntegers
bool HugeInteger::operator==(const HugeInteger & rightSide) {
  //Checks if the HugeIntegers are different sizes
  if(this->digits.size() > rightSide.digits.size() || this->digits.size() > rightSide.digits.size()) {
    return false;
  }
  //Checks if each number of the HugeIntegers are the same
  else {
    for(int i = rightSide.digits.size() - 1; i >= 0; i--) {
      if(rightSide.digits[i] > this->digits[i] || this->digits[i] > rightSide.digits[i]) {
        return false;
      }
    }
  }
  return true;
}

//Overloads the + operator so it can add two HugeIntegers
const HugeInteger HugeInteger::operator+(const HugeInteger & rightSide) {
  HugeInteger HTemp = HugeInteger();
  int remain = 0;
  HTemp.digits[0] = this->digits[0] + rightSide.digits[0];
  if(this->digits[0] + rightSide.digits[0] > 9) {
    HTemp.digits[0] = HTemp.digits[0] - 10;
    remain = 1;
  }
  //Checks if this is larger than rightSide
  if(this->digits.size() > rightSide.digits.size()) {
    for(int i = 1; i < rightSide.digits.size(); i++) {
      HTemp.digits.push_back(this->digits[i] + rightSide.digits[i] + remain);
      //Checks if the addition has gone over 9 into double digits and creates a remainder placeholder
      if(this->digits[i] + rightSide.digits[i] + remain > 9) {
        HTemp.digits[i] = HTemp.digits[i] - 10;
        remain = 1;
      }
      else {
        remain = 0;
      }
    }
    //Finishes the number with the remaining numbers of this
    if(HTemp.digits.size() < this->digits.size()) {
      for(int i = rightSide.digits.size(); i < this->digits.size(); i++) {
        HTemp.digits.push_back(this->digits[i] + remain);
        remain = 0;
      }
    }
  }
    //Same as above but for the cases that rightSide is greater than this or they are equal
    else {
      for(int i = 1; i < this->digits.size(); i++) {
        HTemp.digits.push_back(this->digits[i] + rightSide.digits[i] + remain);
        if(this->digits[i] + rightSide.digits[i] + remain > 9) {
          HTemp.digits[i] = HTemp.digits[i] - 10;
          remain = 1;
        }
        else {
          remain = 0;
        }
      }
      if(HTemp.digits.size() < rightSide.digits.size()) {
        for(int i = this->digits.size(); i < rightSide.digits.size(); i++) {
          HTemp.digits.push_back(rightSide.digits[i] + remain);
          remain = 0;
      }
    }
  }
  if(remain == 1) {
    HTemp.digits.push_back(1);
  }
  return HTemp;
}


//Overloads the > operator so that it can compare two HugeIntegers
bool HugeInteger::operator>(const HugeInteger & rightSide) {
  //Checks if one HugeInteger is larger than the other
  if(this->digits.size() > rightSide.digits.size()) {
    return true;
  }
  else if(this->digits.size() < rightSide.digits.size()) {
    return false;
  }
  //Cycles throught the digits of the HugeIntegers and checks which is larger
  else {
    for(int i = rightSide.digits.size() - 1; i >= 0; i--) {
      if(rightSide.digits[i] > this->digits[i]) {
        return false;
      }
      else if(rightSide.digits[i] < this->digits[i]) {
        return true;
      }
    }
  }
  return false;
}
//Checks if the given HugeInteger is zero
bool HugeInteger::isZero() const {
  for(int i = 0; i < this->digits.size(); i++) {
    if(this->digits[i] != 0) {
      return false;
    }
  }
  return true;
}

//Returns a string representation of the HugeInteger
string HugeInteger::toString() const {
  stringstream stream;
  for(int i = digits.size() - 1; i >= 0; i--) {
    stream << digits[i];
  }

  string result = stream.str();
  return result;
}
