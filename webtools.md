---
layout: default
title: ESP Web Tools
header_type: post
show_breadcrumb: false
---

# Installation

You can use the button below to install the pre-built firmware directly to your device via USB from the browser. Note this has already been done from the factory. You only need to do this if you're trying to start fresh, or grap the latest updates.

<ul class="radios">
    <li>
        <label><input type="radio" name="type" value="bpmk1" />Bed Presence Mk1</label>
    </li>
    <li>
        <label><input type="radio" name="type" value="test" />2nd Test Device</label>
    </li>
</ul>
<p class="button-row" align="center">
    <esp-web-install-button class="btn btn-outline-warning invisible"></esp-web-install-button>
</p>

<esp-web-install-button manifest="../../manifest.json"></esp-web-install-button>

<script type="module" src="https://unpkg.com/esp-web-tools@10/dist/web/install-button.js?module"></script>
<script>
  document.querySelectorAll('input[name="type"]').forEach(radio =>
    radio.addEventListener("change", () => {
      const button = document.querySelector('esp-web-install-button');
      button.manifest = `https://docs.elevatedsensors.com/${radio.value}-manifest.json`;
      button.classList.remove('invisible');
    }
  ));
</script>
