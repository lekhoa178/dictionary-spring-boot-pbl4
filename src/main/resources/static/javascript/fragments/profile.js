// /*<![CDATA[*/
//
// // Assuming 'userExperienceData' is a list of objects with 'date', 'experience', and 'onlineHours' properties
// var userStats = /*[[${statPerDay}]]*/ [];
//
// var ctx = document.getElementById('statChart').getContext('2d');
//
// // Prepare data
// var dates = userStats.map(entry => entry.id.dayId);
// var experienceValues = userStats.map(entry => entry.experience);
// var onlineHoursValues = userStats.map(entry => entry.onlineHours);
//
// // Create the chart
// var chart = new Chart(ctx, {
//     type: 'line',
//     data: {
//         labels: dates,
//         datasets: [
//             {
//                 label: 'Experience',
//                 data: experienceValues,
//                 borderColor: 'rgba(75, 192, 192, 1)',
//                 fill: false
//             },
//             {
//                 label: 'Online Hours',
//                 data: onlineHoursValues,
//                 borderColor: 'rgba(255, 99, 132, 1)',
//                 fill: false
//             }
//         ]
//     },
//     options: {
//         responsive: true,
//         maintainAspectRatio: false
//     }
// });
//
// /*]]>*/