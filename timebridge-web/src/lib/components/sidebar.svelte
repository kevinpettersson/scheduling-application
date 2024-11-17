<script lang="ts">
	import * as Sidebar from '$lib/components/ui/sidebar/index.js';
	import ModeToggle from './mode.svelte';
	import { Button } from '$lib/components/ui/button/index.js';
</script>

<Sidebar.Root variant="floating">
	<Sidebar.Content>
		<Sidebar.Group>
			<Sidebar.GroupContent></Sidebar.GroupContent>
		</Sidebar.Group>
	</Sidebar.Content>
	<Sidebar.Footer class="flex-row">
		<ModeToggle />
		<Button class="flex-grow">Export Calendar</Button>
	</Sidebar.Footer>
</Sidebar.Root>
