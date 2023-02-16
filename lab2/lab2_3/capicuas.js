capicuasPhones = function() {
    var allPphones = db.phones.find({}, {"display": 1, "_id": 0}).toArray();
    var ctr = 0;
    for (let i = 0; i < allPphones.length; i++) {
        var phoneDigits = phones[i].display.split("-")[1];
        if (phoneDigits.split("") == phoneDigits.split("").reverse()) {
            print(ctr, " ", phoneDigits);
            ctr += 1; 
        }
    }
    print("Número de capículas no sistema: ", ctr) 
}
