def interweaving(x: str, y: str, s: str, i: int, j: int, k: int):
    newX = i
    newY = j
    if k == len(s):
        return True
    elif x[i] != s[k] and y[j] != s[k]:
        return False
    else:
        if len(x) == i + 1:
            newX = 0
        else:
            newX = i + 1
        if len(y) == j + 1:
            newY = 0
        else:
            newY = j + 1
        if x[i] == s[k] and y[j] == s[k]:
            return interweaving(x,y,s, newX, j, k+1) or interweaving(x,y,s,i,newY,k+1)
        elif x[i] == s[k]:
            return interweaving(x,y,s,newX,j,k+1)
        else:
            return interweaving(x,y,s,i,newY,k+1)

print(interweaving('101','10','101101100',0,0,0))
