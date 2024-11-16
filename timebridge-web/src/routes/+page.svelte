<script lang="ts">
	// Components
	import * as Form from '$lib/components/ui/form/index.js';
	import { Input } from '$lib/components/ui/input/index.js';

	// Form validation
	import { superForm, defaults } from 'sveltekit-superforms';
	import { zod } from 'sveltekit-superforms/adapters';
	import { _iCalSchema, type iCalSchema } from '$lib/schemas';

	// Validate the form and send data to an external API
	let data;
	
	const form = superForm(defaults(zod(_iCalSchema)), {
		SPA: true,
		validators: zod(_iCalSchema),
		async onUpdate({ form }) {
			if (form.valid) {
				// Call an external API with form.data, await the result and update form
				data = await fetch('http://localhost:8080/download', {
					method: 'POST',
					headers: {
						'Content-Type': 'application/json',
						'ical': form.data.url
					},
					body: JSON.stringify(form.data)
				}).then((res) => res.json());
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
