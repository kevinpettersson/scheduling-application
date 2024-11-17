<script lang="ts">
	import * as Form from '$lib/components/ui/form/index.js';
	import { Input } from '$lib/components/ui/input/index.js';
	import { superForm, defaults } from 'sveltekit-superforms';
	import { zod } from 'sveltekit-superforms/adapters';
	import { _iCalSchema, type iCalSchema } from '$lib/schemas';
	import { goto } from '$app/navigation';
	import { calendarStore } from '$lib/stores/session-store';

	const form = superForm(defaults(zod(_iCalSchema)), {
		SPA: true,
		validators: zod(_iCalSchema),
		async onUpdate({ form }) {
			// Call an external API with form.data, await the result and update form
			if (form.valid) {
				let request = `http://localhost:8080/upload?ical=${form.data.url}`;

				const response = await fetch(request, {
					method: 'GET',
					headers: {
						'Content-Type': 'application/json',
					}
				});

				if (!response.ok) {
					throw new Error(`Failed to upload calendar: ${response.statusText}`);
				}
				else {
					calendarStore.set(await response.json());
					goto('/editor');
				}
			}
		}
	});

	const { form: formData, enhance } = form;
</script>

<form method="GET" use:enhance>
	<Form.Field {form} name="url">
		<Form.Control>
			{#snippet children({ props })}
				<Form.Label>Calendar</Form.Label>
				<Input {...props} bind:value={$formData.url} />
			{/snippet}
		</Form.Control>
		<Form.Description>This is your public display name.</Form.Description>
		<Form.FieldErrors />
	</Form.Field>
	<Form.Button>Submit</Form.Button>
</form>
