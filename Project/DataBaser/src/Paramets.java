public interface Paramets {
        ////////////////////////////////////////////////////
        int FrameSizeX=750;
        int FrameSizeY=550;
        String FrameApp="Zaliczenie";
        ////////////////////////////////////////////////////
        int PanelSizeXLeft=250;
        int PanelSizeXRight=FrameSizeX-PanelSizeXLeft;
        ////////////////////////////////////////////////////
        String[] RightLabelInfo = {"Nazwa","Kategoria","Data","Ilosc"};
        ////////////////////////////////////////////////////
        int ListOfItemsPanelX=PanelSizeXRight-40;
        int ListOfItemsPanelY=FrameSizeY-70;

        ////////////////////////////////////////////////////
        String[] ErrorList = {"Nie wpisales wszystkich wartosci produktu","Podaj ID przedmiotu","Data","Ilosc"};
        String FatalError = "Blad fatalny";

        int[] errorSize = {400,200};
        int[] errorInfoSize={50,50};

        ////////////////////////////////////////////////////
        String[] NameofFile= {"zasoby.txt"};
        String logo="logo.png";
        int[] logosize={50,50};
        String path="warring.png";
}
