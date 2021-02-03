program Main;
    var a, b : integer;
    var c : real;

    procedure add(x, y : integer);
        var sum : integer;

        begin {add}
            sum := x + y;
        end; {add}

begin {Main}
    a := 10;
    b := 3;
    c := 7 + 3 * (a div (12 / (b + 1) - 1));
end. {Main}