<script lang="ts">
	import EventTable from '$lib/components/event-table.svelte';
	import Button from '$lib/components/ui/button/button.svelte';
</script>

<div class="flex h-full flex-auto flex-col gap-2">
	<div class="br-8 h-full overflow-scroll rounded-md border">
		<EventTable />
	</div>
</div>
