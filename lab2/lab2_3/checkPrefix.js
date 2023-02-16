checkPrexiCount = function () {
    return db.phones.aggregate([{$group: {_id: "$components.prefix", Count: {$sum: 1}}}]);
}
