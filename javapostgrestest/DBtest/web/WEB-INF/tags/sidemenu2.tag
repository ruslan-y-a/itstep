<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="categories">
<form class="sform">
       <label for="sort">Sort:</label>
	   <select class="sform__text-input" id="sort" name="sort">
	   <option value=1>Ascending price</option>
	   <option value=2>Descending price</option>			              
	   </select>			
    <label class="sform__label" for="material">Material:</label>
		<select class="sform__text-input" id="material" name="Material" multiple>
		    <option value="Natural leather">Natural leather</option>
		    <option value="Faux leather">Faux leather</option>
			<option value="Textile">Textile</option>
			<option value="Rubber">Rubber</option>
			<option value="Suede">Suede</option>
		</select>
    <label class="sform__label" for="color">Color:</label>
		<select class="sform__text-input" id="color" name="Color" multiple>
		    <option value="white">white</option>
		    <option value="black">black</option>
			<option value="brown">brown</option>
			<option value="red">red</option>
			<option value="pink">pink</option>
			<option value="blue">blue</option>
			<option value="orange">orange</option>
			<option value="yellow">yellow</option>
			<option value="green">green</option>
			<option value="grey">grey</option>
			<option value="bordo">bordo</option>
			<option value="silver">silver</option>			
			<option value="gold">gold</option>			
			<option value="purple">purple</option>			
			<option value="multicolor">multicolor</option>						
		</select>
	<label class="sform__label" for="size">Size:</label>
		<select class="sform__text-input" id="size" name="Size" multiple>
		    <option value=36>36</option>
		    <option value=37>37</option>
			<option value=38>38</option>
			<option value=39>39</option>
			<option value=40>40</option>
			<option value=41>41</option>
			<option value=42>42</option>
			<option value=43>43</option>
			<option value=44>44</option>
			<option value=45>45</option>
			<option value=46>46</option>
			<option value=47>47</option>			
			<option value=48>48</option>							
		</select>	
    <label class="sform__label" for="prices">Prices:</label>
		<select class="sform__text-input" id="prices" name="Prices" multiple>
		    <option value=1>under 50</option>
		    <option value=2>50-100</option>
			<option value=3>above 100</option>
		</select>
    <label class="sform__label" for="discounts">Discounts:</label>
		<select class="sform__text-input" id="discounts" name="Discounts" multiple>
		    <option value=1>0</option>
		    <option value=2>under 20</option>
			<option value=3>20-40</option>
			<option value=4>above 40</option>
		</select>
    <div class="sform__input"><input id="stock" type="checkbox" name="In stock" value="true" checked><label for="stock">In Stock</label></div> 
	<button class="form__button" type="submit">Select</button>						
	<button id="button-cancel" class="form__button" type="reset">Cancel</button>
 </form>
</div>