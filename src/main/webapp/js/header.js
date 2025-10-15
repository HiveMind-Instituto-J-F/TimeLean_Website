let lastScroll = 0;
const header = document.querySelector('header');

window.addEventListener('scroll', () => {
  const currentScroll = window.pageYOffset;

  if (currentScroll > lastScroll) {
    header.classList.add('hide');
    header.setAttribute('inert', '');
  } else {
    header.classList.remove('hide');
    header.removeAttribute('inert');
  }

  lastScroll = currentScroll;
});