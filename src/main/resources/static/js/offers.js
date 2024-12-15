document.addEventListener('DOMContentLoaded', () => {
    const forms = document.querySelectorAll('.favorite-form');

    forms.forEach(form => {
        form.addEventListener('submit', async (event) => {
            event.preventDefault();

            const actionUrl = form.getAttribute('action');

            try {
                const response = await fetch(actionUrl, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });
            } catch (error) {
                console.error('Erreur lors de l\'ajout aux favoris:', error);
                alert('Impossible d\'ajouter l\'offre aux favoris. Veuillez réessayer.');
            }
        });
    });
});

document.addEventListener('DOMContentLoaded', () => {
    const forms = document.querySelectorAll('.purchase-form');

    forms.forEach(form => {
        form.addEventListener('submit', async (event) => {
            event.preventDefault();

            const actionUrl = form.getAttribute('action');

            try {
                 const response = await fetch(actionUrl, {
                     method: 'POST',
                     headers: {
                        'Content-Type': 'application/json',
                     },
                });
                if (response.ok) {
                    location.reload();
                }
            } catch (error) {
                console.error('Erreur lors de l\'achat:', error);
                alert('Erreur lors de l\'achat.');
            }
        });
    });
});