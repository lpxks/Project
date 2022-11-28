const getCodes = (params) => {
  return $axios({
      url:"/code/getCode",
      method: 'get',
      params
  })
}

// const getOrderDetailPage = (params) => {
//     return $axios({
//         url: '/order/page',
//         method: 'get',
//         params
//     })
// }