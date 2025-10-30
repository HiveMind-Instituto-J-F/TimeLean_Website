document.querySelectorAll('.toggle-visibility').forEach(button => {
  button.addEventListener('click', (e) => {
    e.preventDefault();

    const row = button.closest('tr');
    const sensitiveCells = row.querySelectorAll('.sensitive');
    const eyeOpen = button.querySelector('.open');
    const eyeClosed = button.querySelector('.closed');

    const isActive = button.classList.toggle('eye-active');

    sensitiveCells.forEach(cell => {
      const real = cell.dataset.real ?? '';
      const isMoney = cell.classList.contains('money');

      if (isActive) {
        // Mostrar valor real
        if (isMoney) {
          // adiciona "R$ " se não existir
          cell.textContent = real.startsWith('R$') ? real : 'R$ ' + real;
        } else {
          cell.textContent = real;
        }
      } else {
        // Esconder valor
        if (isMoney) {
          // Quando ocultar, só exibe "R$ " sem números
          cell.textContent = 'R$ ' + '*'.repeat(real.replace(/[^\d.,]/g, '').length || 4);
        } else {
          cell.textContent = '*'.repeat(real.length || 6);
        }
      }
    });

    // Controlar ícones
    if (eyeOpen && eyeClosed) {
      if (isActive) {
        eyeOpen.style.display = 'none';
        eyeClosed.style.display = 'inline';
      } else {
        eyeOpen.style.display = 'inline';
        eyeClosed.style.display = 'none';
      }
    }
  });
});
