
# EReport

f = open("employees.dat", "r")
file = f.readlines()

temp = []
fname = []
number = []
name = []

for x in file:
    
    if ('#' in x):
        file.remove(x)
        continue
    
    temp = x.split(",")
    number.append(temp[0])
    fname = temp[1].split()
    name.append(fname[1])

for x in file:
    if ('#\n' not in file):
        break
    else:
        file.remove('#\n')


bynum = list(range(len(number)))
byname = list(range(len(name)))

def quicksort(l, data, indexl, indexr):
    if(indexl >= indexr):
        return
    else:
        i = pnd(l, data, indexl, indexr)
        quicksort(l, data, indexl, i-1)
        quicksort(l, data, i+1, indexr)

def pnd(l, data, indexl, indexr):
    piv = data[l[indexr]]
    wall = indexl -1
    tem = 0
    for i in range(indexl, indexr):
        if( data[l[i]] < piv ):
            wall += 1
            tem = l[i]
            l[i] = l[wall]
            l[wall] = tem

    tem = l[indexr]
    l[indexr] = l[wall+1]
    l[wall+1] = tem
    return wall+1

quicksort(bynum, number, 0, len(number)-1)
quicksort(byname, name, 0, len(name)-1)

print('Processing by employee number...\n')

for i in bynum:
    print(file[i])

print('\nProcessing by last (family) Name...\n')

for j in byname:
    print(file[j])

    

