from distutils.cmd import Command
from tkinter import *
from random import randint
from tkinter import messagebox
count = 0
count2 = 0
matrix_B = []
matrix_I = []
answer_dict = {}
dict = []
minidict = []
matrix_dict = []
occ = 0
ok90 = 0
ok180 = 0
ok270 = 0

class FindPattern():

    #init function
    def __init__(self):
        self.all_buttons = []
        self.all_buttons2 = []
        self.matrice = []
        self.matrice2 = []
        self.answer_list = []
        self.answer_list2 = []
        self.finestra = Tk()
        self.finestra.title('Ricerca Pattern')
        self.finestra.geometry("800x800")
        self.finestra.frame = Frame()
        self.finestra.frame.pack()
        self.txt1 = Entry(self.finestra)
        self.txt1.pack()
        btn1 = Button(self.finestra, text="Imposta Righe", command=lambda:[self.setRighe, btn1.pack_forget()])
        btn1.pack()
        self.txt2 = Entry(self.finestra)
        self.txt2.pack()
        btn2 = Button(self.finestra, text="Imposta Colonne", command=lambda:[self.setColonne, btn2.pack_forget()])
        btn2.pack()
        btn3 = Button(self.finestra, text="Crea Matrice", command=self.creaMatrice)
        btn3.pack()
        btnmatrix2 = Button(self.finestra, text="Crea Pattern", command=self.creaMini)
        btnmatrix2.pack(padx=200, pady=30)
        btn4 = Button(self.finestra, text="Exit Game", command=self.exitGame)
        btn4.pack()
        btnCheckPattern = Button(text="Trova Pattern nella Matrice", command=self.FindPattern)
        btnCheckPattern.place(x=25, y=300)

    def setRighe(self):
        print(self.txt1.get())
        self.rows = int(self.txt1.get())

    def setColonne(self):
        print(self.txt2.get())
        self.cols = int(self.txt2.get())        

    def creaMatrice(self):
        rows = int(self.txt1.get())
        cols = int(self.txt2.get()) 
        print("Rows : ", rows, " - COLS : ", cols)
        self.matrice3 = [[0 for _ in range(cols)] for _ in range(rows)]
        self.matrice = [[0 for _ in range(rows+1)] for _ in range(cols+1)]
        for y in range(rows):
            buttons_row = []
            for x in range(cols):
                value = randint(0,1)
                boton = Button(self.finestra.frame, text=value,fg="blue",width=2, height=2)
                boton.grid(row=y, column=x)
                buttons_row.append( boton )
                self.matrice3[y][x] = boton["text"]
                dict.append(boton)
            self.all_buttons.append( buttons_row )

        self.matrix_B = self.matrice3.copy()
        print("Matrice3 : ", self.matrice3)
        print("Dictionary : ", dict)

    # funzioni per la minimatrice
    def creaMini(self):
        self.root = Tk()
        self.root.title("Mini Matrix Frame")
        self.root.geometry("500x500")
        self.root.frame2 = Frame()
        self.root.frame2.pack()
        self.txt3 = Entry(self.root)
        self.txt3.pack()
        self.btnrow = Button(self.root, text="Set Rows", command=lambda:[self.setRowsMini])
        self.btnrow.pack()
        self.txt4 = Entry(self.root)
        self.txt4.pack()
        self.btncol = Button(self.root, text="Set Cols", command=self.setColsMini)
        self.btncol.pack()
        self.btncrea = Button(self.root, text="Generate mini Matrix", command=self.generateMini)
        self.btncrea.pack()
        self.btnexit = Button(self.root, text="Exit", command=self.root.destroy)
        self.btnexit.pack()

    def setRowsMini(self):
            print("Row minimatrix",self.txt3.get())
            self.rows2 = int(self.txt3.get())
            if(self.rows2 == '' or self.rows2 <= 0):
                messagebox.showinfo("Error Rows", "Error in typing rows number!")
    def setColsMini(self):
            print("Cols minimatrix",self.txt4.get())
            self.cols2 = int(self.txt4.get())
            if (self.cols2 == '' or self.cols2 <= 0):
                messagebox.showinfo("Error Columns", "Error in typing cols number!")

    def generateMini(self):
        rows1 = int(self.txt1.get())
        cols1 = int(self.txt2.get())
        rows2 = int(self.txt3.get())
        cols2 = int(self.txt4.get())
        print("Rows : ", rows2, " - COLS : ", cols2)
        if(rows2 <= rows1 or cols2 <= cols1):
            self.matrice2 = [[0 for _ in range(cols2)] for _ in range(rows2)]
            self.mmatrice = [[0 for _ in range(rows2 + 1)] for _ in range(cols2 + 1)]
            for y in range(rows2):
                btns_row = []
                for x in range(cols2):
                    botton2 = Button(self.finestra.frame, text=0, width=2, height=2, fg="blue", command=lambda a=x, b=y: self.onButtonMiniPressed(a, b))                
                    botton2.grid(row=5+y, column=5+x)
                    btns_row.append(botton2)
                    self.matrice2[y][x] = botton2["text"]
                    minidict.append(botton2)
                self.all_buttons2.append(btns_row)
            print("Matrice2 : ", self.matrice2)
            print("MiniDict : ", minidict)
        else:
             messagebox.showinfo("Errore nella scelta del pattern", "Numero di righe/colonne troppo grande per la matrice di partenza")
             rows1 = 0
             cols1 = 0
        
        

    def onButtonMiniPressed(self, i, j):
        global count2, matrice2, answer_list2
        if self.all_buttons2[j][i]['text'] == 0 and count2 < 100:
                self.all_buttons2[j][i]['text'] = 1
                count2 += 1
                # prima dell'incremento stampo la lista answer_list
                print("count2 : ", count2)
                print("M_text : ",self.all_buttons2[j][i]["text"])
                self.matrice2[j][i] = self.all_buttons2[j][i]["text"]
                self.answer_list2.append(self.all_buttons2[j][i]["text"])
                minidict.append(self.all_buttons2[j][i])
                print(self.answer_list2)
                print("minidict : ",minidict)
        elif self.all_buttons2[j][i]['text'] == 1 and count2 < 100:
                self.all_buttons2[j][i]['text'] = 0
                count2 += 1
                # prima dell'incremento stampo la lista answer_list
                print("count2 : ", count2)
                print("M_text : ",self.all_buttons2[j][i]["text"])
                self.matrice2[j][i] = self.all_buttons2[j][i]["text"]
                self.answer_list2.append(self.all_buttons2[j][i]["text"])
                minidict.append(self.all_buttons2[j][i])
                print(self.answer_list2)
                print("minidict : ",minidict)
        elif count2 >= 100:
                # reimposto valori di default
                count2 = 0

    def run(self):
        self.finestra.mainloop()

    def exitGame(self):
        self.finestra.destroy()

    def patternCheck(self, b_r, b_c):
        global occ
        rI = len(self.matrix_I)
        cI = len(self.matrix_I[0])
        reset_bc = b_c
        ok = 0
        same = 0
        maxsame = rI * cI
        for i_r in range(0, rI):
            b_c = reset_bc
            for i_c in range(0, cI):
                if self.matrix_I[i_r][i_c] == self.matrix_B[b_r][b_c]:
                    same += 1
                b_c += 1
            b_r += 1
        if same == maxsame:
            ok = 1
            occ += 1
            print("Ok : ", occ)
        return ok

    def cercaP(self, dest, init):
        rows = int(self.txt1.get())
        cols = int(self.txt2.get()) 
        global ok
        self.matrix_I = init.copy()
        self.matrix_B = dest.copy()
        trovati = 0
        ok = 0
        rlimit = cols - (len(self.matrix_I[0]) - 1)
        # num colonne grande - (num colonne piccola - 1)
        climit = rows - (len(self.matrix_I) - 1)
        # num righe grande - (num righe piccola - 1)
        dimR = len(self.matrix_I)
        dimC = len(self.matrix_I[0])
        dimRB = len(self.matrix_B)
        dimCB = len(self.matrix_B[0])
        if dimC > dimR:
            #caso in cui matrix_I è lunga
            if climit > rlimit:
                tempR = rlimit
                rlimit = climit
                climit = tempR

        elif dimC < dimR:
            if climit < rlimit:
                tempR = rlimit
                rlimit = climit
                climit = tempR

        else:
            #caso in cui è quadrata
            if dimRB > dimCB:
                if climit > rlimit:
                    tempR = rlimit
                    rlimit = climit
                    climit = tempR
            else:
                if climit < rlimit:
                    tempR = rlimit
                    rlimit = climit
                    climit = tempR

        for i in range(rlimit):
            for c in range(climit):
                ok = self.patternCheck(i, c)
                trovati = trovati + ok
                ok = 0  # azzeramento di ok

        return trovati

    def RotateM(self, m):
        new_matrix =  [[m[j][i] for j in range(len(m))] for i in range(len(m[0]) - 1, -1, -1)]
        print("Tm rotated of (other) 90° = ", new_matrix)
        Rm = new_matrix.copy()
        return Rm

    def FindPattern(self):
        rows = int(self.txt1.get())
        cols = int(self.txt2.get()) 
        global matrice2, rows2, cols2, matrix_B
        print("Sei finito in 'Find Pattern'!")
        print("righe matrice bottoni : ", rows, " e colonne : ", cols)
        print("Matrice2 contiene : ", self.matrice2)
        m = []
        mb = []  # matrix with buttons values
        m = self.matrice2.copy()
        mb = self.matrix_B.copy()
        ris = self.cercaP(mb, m) #trovo pattern a 0°
        print("----------------------------\n")
        print("Checking for 90° pattern...\n")
        mT = self.RotateM(m)
        print("Pattern da cercare ruotato di 90° = ", mT)
        print("Tm is = ", mT)
        ris90 = self.cercaP(mb, mT)
        print("----------------------------\n")
        print("Checking for 180° Matrix...\n")
        mTT = self.RotateM(mT)
        print("Pattern da cercare ruotato a 180° = ", mTT)
        ris180 = self.cercaP(mb, mTT)
        print("----------------------------\n")
        print("Checking for 270° Matrix...\n")
        m270 = self.RotateM(mTT)
        print("Pattern da cercare ruotato a 270° = ", m270)
        ris270 = self.cercaP(mb, m270)
        print("----------------------------\n")
        messagebox.showinfo("Statistics",f"Pattern 0° = {ris}\nPattern 90° = {ris90}\nPattern 180° = {ris180}\nPattern 270° = {ris270}")
        print("#volte pattern a 0° = ", ris)
        print("#volte pattern a 90° = ", ris90)
        print("#volte pattern a 180° = ", ris180)
        print("#volte pattern a 270° = ", ris270)
FindPattern().run()