const $ = (id) => document.getElementById(id)

const upload = () => {
  let form = new FormData()
  console.log($('fileInput').files)
  form.append('file', $('fileInput').files[0])
  fetch('./decrypt', {method: 'post', body: form})
    .then(res => res.json())
    .then(data => console.log(data))
    .catch(e => console.log(e))
}

$('upBtn').addEventListener('click', upload)