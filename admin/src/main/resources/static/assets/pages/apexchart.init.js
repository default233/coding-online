
/*
 Template Name: Zegva - Responsive Bootstrap 4 Admin Dashboard
 Author: Themesdesign
 Website: www.themesdesign.in
 File: spakline chart init js
 */



var options1 = {
    chart: {
      type: 'area',
      height: 60,
      sparkline: {
        enabled: true
      }
    },
    series: [{
      data: [24, 66, 42, 88, 62, 24, 45, 12, 36, 10]
    }],
    stroke: {
      curve: 'smooth',
      width: 3
    },
    
    colors: ['#59c6fb'],
    tooltip: {
      fixed: {
        enabled: false
      },
      x: {
        show: false
      },
      y: {
        title: {
          formatter: function (seriesName) {
            return ''
          }
        }
      },
      marker: {
        show: false
      }
    }
  }
  
  new ApexCharts(document.querySelector("#chart1"), options1).render();

// 2
  
var options2 = {
    chart: {
      type: 'area',
      height: 60,
      sparkline: {
        enabled: true
      }
    },
    series: [{
        data: [54, 12, 24, 66, 42, 25, 44, 12, 36, 9]
    }],
    stroke: {
      curve: 'smooth',
      width: 3
    },
    
    colors: ['#02c58d'],
    tooltip: {
      fixed: {
        enabled: false
      },
      x: {
        show: false
      },
      y: {
        title: {
          formatter: function (seriesName) {
            return ''
          }
        }
      },
      marker: {
        show: false
      }
    }
  }
  
  new ApexCharts(document.querySelector("#chart2"), options2).render();

  
// 3

var options3 = {
    chart: {
      type: 'area',
      height: 60,
      sparkline: {
        enabled: true
      }
    },
    series: [{
        data: [10, 36, 12, 44, 63, 24, 44, 12, 56, 24]
    }],
    stroke: {
      curve: 'smooth',
      width: 3
    },
    
    colors: ['#fcbe2d'],
    tooltip: {
      fixed: {
        enabled: false
      },
      x: {
        show: false
      },
      y: {
        title: {
          formatter: function (seriesName) {
            return ''
          }
        }
      },
      marker: {
        show: false
      }
    }
  }
  
  new ApexCharts(document.querySelector("#chart3"), options3).render();

//   4
  
var options4 = {
    chart: {
      type: 'area',
      height: 60,
      sparkline: {
        enabled: true
      }
    },
    series: [{
      data: [34, 66, 42, 33, 62, 24, 45, 16, 48, 10]
    }],
    stroke: {
      curve: 'smooth',
      width: 3
    },
    
    colors: ['#fc5454'],
    tooltip: {
      fixed: {
        enabled: false
      },
      x: {
        show: false
      },
      y: {
        title: {
          formatter: function (seriesName) {
            return ''
          }
        }
      },
      marker: {
        show: false
      }
    }
  }
  
  new ApexCharts(document.querySelector("#chart4"), options4).render();