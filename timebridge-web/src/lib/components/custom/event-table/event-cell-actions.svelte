<script lang="ts">
	import * as Table from '$lib/components/ui/table/index.js';
	import { Button } from '$lib/components/ui/button/index.js';
	import Trash from 'lucide-svelte/icons/trash-2';
	import type { Event } from '$lib/types/calendar.d.ts';

	let { event } = $props<{ event: Event }>();
</script>

<Table.Cell>
	<Button variant="destructive" size="icon">
		<Trash />
	</Button>
</Table.Cell>
