$(function () {
  const statEl = document.getElementById('profile-stats');

  const days = JSON.parse(statEl.dataset.days.replace(/([0-9-]+)/g, '"$1"'));
  const exps = JSON.parse(statEl.dataset.exps);
  const onlineHours = JSON.parse(statEl.dataset.onlineHours);

  const max = Math.max(...exps) + 5;
  console.log(max);

  Highcharts.chart('chart', {
    chart: {
      type: 'line',
    },
    title: {
      text: 'Thống kê quá trình học tập',
    },
    subtitle: {
      text: '',
    },
    xAxis: {
      type: 'datetime',
      categories: days,
      drilldow: true,
    },
    yAxis: {
      min: 0,
      max: max,
      title: {
        text: 'xp/hours',
      },
    },
    tooltip: {
      headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
      pointFormat:
        '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
        '<td style="padding:0"><b>{point.y:f}</b></td></tr>',
      footerFormat: '</table>',
      shared: true,
      useHTML: true,
    },

    plotOptions: {
      line: {
        pointPadding: 0.2,
        borderWidth: 0,
      },
    },
    series: [
      {
        name: 'Experiences',
        data: exps,
      },
      {
        name: 'Online Hours',
        data: onlineHours,
      },
    ],

    responsive: {
      rules: [
        {
          condition: {
            maxWidth: 500,
          },
          chartOptions: {
            legend: {
              layout: 'horizontal',
              align: 'center',
              verticalAlign: 'bottom',
            },
          },
        },
      ],
    },
  });
});

$('#edit-btn').on('click', function (e) {
  let nameTxt = document.querySelector('.profile-text--title');
  let maleBtn = document.getElementById('male-btn');
  let femaleBtn = document.getElementById('female-btn');
  let birthDatePicker = document.querySelector('.profile-text--birthDate');
  let emailTxt = document.querySelector('.profile-text--email');

  nameValue = document.querySelector('.profile-text--title').value;

  let genderBtns = document.getElementsByName('gender-rd');

  for (let i = 0; i < genderBtns.length; i++) {
    if (genderBtns[i].checked === true) genderValue = genderBtns[i].value;
  }

  birthDateValue = document.querySelector('.profile-text--birthDate').value;
  emailValue = document.querySelector('.profile-text--email').value;

  nameTxt.removeAttribute('readonly');

  maleBtn.disabled = false;
  femaleBtn.disabled = false;

  birthDatePicker.removeAttribute('readonly');
  emailTxt.removeAttribute('readonly');

  document.querySelector('.save-btn').classList.remove('hidden');
});

function editProfile() {
  let currentNameValue = document.querySelector('.profile-text--title').value;

  let currentGenderValue;

  let genderBtns = document.getElementsByName('gender-rd');

  for (let i = 0; i < genderBtns.length; i++) {
    if (genderBtns[i].checked === true)
      currentGenderValue = genderBtns[i].value;
  }

  let currentBirthDateValue = document.querySelector(
    '.profile-text--birthDate'
  ).value;
  let currentEmailValue = document.querySelector('.profile-text--email').value;

  let saveBtn = document.querySelector('.save-btn');
  saveBtn.disabled =
    currentNameValue === nameValue &&
    currentGenderValue === genderValue &&
    currentBirthDateValue === birthDateValue &&
    currentEmailValue === emailValue;
}

function submitForm() {
  let formData = new FormData(document.getElementById('profile-form'));

  $.ajax({
    type: 'POST',
    url: '/profile/update',
    data: formData,
    processData: false, // Prevent jQuery from automatically processing the data
    contentType: false,
    success: async function (response) {
      $(fragmentContainer).html(response);
      const script = document.createElement('script');
      const text = document.createTextNode(
        await AJAX(`/javascript/fragments/profile.js`)
      );
      script.appendChild(text);
      fragmentContainer.append(script);
    },
    error: function (error) {
      alert(this.url);
    },
  });
}
fragmentContainer.addEventListener('click', async (e) => {
  e.preventDefault();

  const target = e.target.closest('.friend--add-item-search');
  if (target == null) return;

  let fragment = target.id.replace('fragment-', '');

  history.pushState(history.state, document.title, `/${fragment}`);
  fragmentContainer.innerHTML = await AJAX(`/${fragment}`);

  const path = `/javascript/fragments/${fragment}.js`;
  if (loadedScript.has(path)) {
    return;
  }

  const script = document.createElement('script');
  const text = document.createTextNode(await AJAX(path));
  script.appendChild(text);
  fragmentContainer.append(script);

  loadedScript.add(path);
});
