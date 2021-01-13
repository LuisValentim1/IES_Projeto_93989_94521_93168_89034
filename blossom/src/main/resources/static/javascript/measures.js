$(document).ready(function() {
    measuresChart();
});

function divideData(string){
    data = string.split("DELIMITER");

    lab = [];
    dat = [];
    var val = null;
    for (d of data) {
        val = d.split(" ");

        lab.push(val[0]);
        dat.push(parseInt(val[1]));
    }

    return [lab, dat]
}

function measuresChart() {
    var joinedData = $("#data").val();

    console.log(joinedData);

    var ret = divideData(joinedData);
    lab = ret[0];
    dat = ret[1];
        
    var ctx = document.getElementById('measuresChart').getContext('2d');
    var chart = new Chart(ctx, {
        // The type of chart we want to create
        type: 'line',

        // The data for our dataset
        data: {
            labels: lab,
            datasets: [{
                label: $("#type").val(),
                backgroundColor: 'rgb(255, 99, 132)',
                borderColor: 'rgb(255, 99, 132)',
                data: dat
            }]
        },

        // Configuration options go here
        options: {
            responsive: true,
            legend: {
                position: 'top'
            },
            title: {
                display: true,
                text: 'Measures'
            },
            scales : {
                yAxes : [{
                    ticks : {
                        max : dat.reduce(function(a, b) {
                            return Math.max(a, b)
                        }) + 1,
                        min : 0,
                        stepSize : 1
                    }
                }]
            }
        }
    });
};