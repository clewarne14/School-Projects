/*
* NAMES: Charlie LeWarne, Adrian Ronquillo
* CLASS: CSCI 343
* PROFESSOR: Dr. Murphy
* DATE: May 11, 2020
* SOURCES: All contained in the slides
* Main source used was golang.org and our own work from past semesters in C++ and Java
* SPECIAL INSTRUCTIONS: None
 */

package main

import (
	"fmt"
	"strconv"
)

/*
* HugeInteger struct which holds the data in the HugeInteger
 */
type HugeInteger struct {
	numberholder []int
}

/*
* Initializes the HugeInteger to h1, is the copy constructor
 */
func HugeIntegerC(h1 HugeInteger) HugeInteger {
	var output []int
	output = h1.numberholder

	h2 := HugeInteger{
		numberholder: output}

	return h2
}

/*
* Initializes the HugeInteger to 0, is the constructor for an empty HugeInteger
 */
func HugeIntegerD() HugeInteger {
	var temp []int
	temp = append(temp, 0)
	h := HugeInteger{
		numberholder: temp}
	return h
}

/*
* Initializes the HugeInteger to the input integer
 */
func HugeIntegerN(input int) HugeInteger {
	var temp []int
	s := strconv.Itoa(input)
	for i := len(s) - 1; i >= 0; i-- {
		j, err := strconv.Atoi(string(s[i])) //err checks to see if the number is truly a number
		if err == nil {                      //if it is a number, this gets appended to the numberholder
			temp = append(temp, j)
		}
	}
	h := HugeInteger{
		numberholder: temp}
	return h
}

/*
* Initializes the HugeInteger to the input string
* Do not assume there are not leading zeroes. Octal problem is solved when passing a string
 */
func HugeIntegerS(input string) HugeInteger {
	var temp []int
	var ZF int
	var output []int
	for i := len(input) - 1; i >= 0; i-- { //String is iterated through to add each element to a temporary slice
		j, err := strconv.Atoi(string(input[i]))
		if err == nil {
			if j == 0 {
				ZF++
			} else {
				ZF = 0
			}
			temp = append(temp, j)
		}
	}
	if ZF != 0 { //ZF is created to count the number of leading zeroes
		output = temp[:len(temp)-ZF]
	} else {
		output = temp
	}
	h := HugeInteger{ //HugeInteger is created with the temporary slice being used as the numberholder
		numberholder: output}
	return h
}

/*
* Function to add two HugeIntegers together and to return a third, new HugeInteger
 */
func add(h1, h2 HugeInteger) HugeInteger {
	var temp []int
	var num int
	var rem int
	rem = 0
	if len(h1.numberholder) > len(h2.numberholder) {
		for i := 0; i < len(h2.numberholder); i++ {
			num = h2.numberholder[i] + h1.numberholder[i] + rem
			if num >= 10 {
				num = num - 10
				rem = 1
			} else {
				rem = 0
			}
			temp = append(temp, num)
		}
		for i := len(h2.numberholder); i < len(h1.numberholder); i++ {
			if rem != 1 {
				num = h1.numberholder[i]
			} else {
				num = h1.numberholder[i] + 1
				if num >= 10 {
					num = num - 10
					rem = 1
				} else {
					rem = 0
				}
			}
			temp = append(temp, num)
		}
	} else {
		for i := 0; i < len(h1.numberholder); i++ {
			num = h2.numberholder[i] + h1.numberholder[i] + rem
			if num >= 10 {
				num = num - 10
				rem = 1
			} else {
				rem = 0
			}
			temp = append(temp, num)
		}
		for i := len(h1.numberholder); i < len(h2.numberholder); i++ {
			if rem != 1 {
				num = h2.numberholder[i]
			} else {
				num = h2.numberholder[i] + 1
				rem = 0
			}
			temp = append(temp, num)
		}
	}
	if rem == 1 {
		temp = append(temp, 1)
	}
	h := HugeInteger{
		numberholder: temp}
	return h
}

/*
* Prints out each element in the HugeInteger inputted as the reciever
 */
func toString(h HugeInteger) string {
	var returnString string = ""
	for i := len(h.numberholder) - 1; i >= 0; i-- {
		returnString += strconv.Itoa(h.numberholder[i])
	}
	return returnString
}

/*
* Checks this HugeInteger to see if it contains all zeros
 */
func isZero(h HugeInteger) bool {
	for i := len(h.numberholder) - 1; i >= 0; i-- {
		if h.numberholder[i] != 0 { //if a number is found that isn't 0, then this method returns false
			return false
		}
	}
	return true //if the the hugeinteger is all zeros
}

/*
* Compares two HugeIntegers to see if they are equal
 */
func equals(h1 HugeInteger, h2 HugeInteger) bool {
	if len(h1.numberholder) != len(h2.numberholder) {
		return false
	} //checks to see if the sizes of the hugeintegers are equal, if not then throws false
	for i := 0; i < len(h1.numberholder); i++ { //if the sizes of the hugeintegers are equal
		if h1.numberholder[i] != h2.numberholder[i] {
			return false
		}
	}
	return true //the hugeintegers are equal
}

/*
* Compares two HugeIntegers h1 and h2. If h1 is larger than h2 then true is returned, false otherwise
 */
func greaterThan(h1 HugeInteger, h2 HugeInteger) bool {
	if len(h1.numberholder) > len(h2.numberholder) {
		return true
	} else if len(h1.numberholder) < len(h2.numberholder) {
		return false
	}
	if len(h1.numberholder) == 1 {
		if h1.numberholder[0] > h2.numberholder[0] {
			return true
		} else if h1.numberholder[0] < h2.numberholder[0] {
			return false
		}
		return false
	}
	for i := len(h1.numberholder) - 1; 0 <= i; i-- { //if the lengths of both hugeintegers are equal
		if h1.numberholder[i] > h2.numberholder[i] {
			return true
		} else if h1.numberholder[i] < h2.numberholder[i] {
			return false
		}
	}
	return false //if the above checks do not return anything then the two hugeintegers are equal to eachother

}

/*
* Compares two HugeIntegers h1 and h2. If h2 is larger than h1 then true is returned, false otherwise
 */
func lessThan(h1 HugeInteger, h2 HugeInteger) bool {
	if len(h1.numberholder) < len(h2.numberholder) {
		return true
	} else if len(h1.numberholder) > len(h2.numberholder) {
		return false
	}
	if len(h1.numberholder) == 1 {
		if h1.numberholder[0] < h2.numberholder[0] {
			return true
		} else if h1.numberholder[0] > h2.numberholder[0] {
			return false
		}
		return false
	}
	for i := len(h1.numberholder) - 1; 0 <= i; i-- { //if the lengths of both hugeintegers are equal
		if h1.numberholder[i] < h2.numberholder[i] {
			return true
		} else if h1.numberholder[i] > h2.numberholder[i] {
			return false
		}
	}
	return false //if the above checks do not return anything then the two hugeintegers are equal to eachother

}

func main() {
	a := HugeIntegerD()
	b := HugeIntegerN(9999)
	c := HugeIntegerS("1")
	d := add(b, c)
	e := isZero(a)
	f := equals(b, c)
	g := greaterThan(b, c)
	i := lessThan(b, c)
	var aa string = toString(a)
	var bb string = toString(b)
	var cc string = toString(c)
	var dd string = toString(d)
	fmt.Println(aa)
	fmt.Println(bb)
	fmt.Println(cc)
	fmt.Println(dd)
	fmt.Println(e)
	fmt.Println(f)
	fmt.Println(g)
	fmt.Println(i)
}
